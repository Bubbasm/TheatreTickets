package model.acts.performances;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import model.users.*;
import model.acts.performances.organization.*;
import model.app.TheatreTickets;
import model.acts.events.*;
import model.areas.*;
import model.areas.positions.*;
import model.operations.*;
import model.enums.*;
import model.acts.waitinglist.*;

/**
 * This class describes a performance
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Performance implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Event the performance is for */
    private Event eventAssoc;
    /** Date for the performance */
    private LocalDateTime when;
    /** Map of all Areas and their occupancy in PerformanceArea */
    private Map<NonComposite, PerformanceArea> myAreasPositions = new HashMap<>();
    /** Current status of the performance */
    private PerformanceStatus status = PerformanceStatus.ACTIVE;
    /** Waiting list of all customers that requested entry */
    private WaitingList wl = new WaitingList(this);
    /**
     * This map relates an area with the operations that have been made for the
     * indicated area
     */
    private Map<NonComposite, List<Operation>> opers = new HashMap<>();
    /** Number of areas where all tickets are sold out */
    private int numSoldOut = 0;

    /**
     * Constructor of a performance
     *
     * @param e Event associated
     * @param d Date when the performance takes place
     */
    public Performance(Event e, LocalDateTime d) {
        Set<NonComposite> mainAreas = TheatreTickets.getInstance().getMainArea().getNonCompositeWithin();
        this.eventAssoc = e;
        this.when = d;
        if (d.isBefore(LocalDateTime.now()))
            this.status = PerformanceStatus.EXPIRED;
        for (NonComposite n : mainAreas) {
            /* The organization of storage of each area depends on the type of area */
            if (n instanceof Sitting)
                myAreasPositions.put(n, new PerformanceSitting((Sitting) n, e.getRestrictedCapacity(n), this));
            else if (n instanceof Standing)
                myAreasPositions.put(n, new PerformanceStanding((Standing) n, e.getRestrictedCapacity(n)));

            if (myAreasPositions.get(n).getAvailablePositons() == 0)
                numSoldOut += 1;
        }
        e.addPerformance(this);
    }

    /**
     * Check if an area is sold out for a performance
     * 
     * @param a area to check
     * @return true if it is sold out, false otherwise
     */
    public boolean isSoldOut(NonComposite a) {
        return myAreasPositions.get(a).isSoldOut();
    }

    /**
     * Check the available positions in an area
     * 
     * @param a area to check
     * @return the number of available positions in the area for this performance
     */
    public int remainingPositions(NonComposite a) {
        return myAreasPositions.get(a).getAvailablePositons();
    }

    /**
     * Checks if all seats indicated are available
     *
     * @param area Area to be checked in
     * @param s    Set of seats to check
     * @return True if all seats are available
     */
    public boolean availableSeats(Sitting area, Set<Seat> s) {
        for (Seat i : s) {
            if (!availableSeat(area, i))
                return false;
        }
        return true;
    }

    /**
     * Computes if the seat indicated is available
     *
     * @param area Area to be checked in
     * @param s    Seat to be considered
     * @return True if seat is available
     */
    public boolean availableSeat(Sitting area, Seat s) {
        PerformanceArea p = myAreasPositions.get(area);
        if (p == null)
            return false;
        /*
         * Note: this should never occur, as there cannot be modifications in a theatre
         * after a performance has been created
         */
        if (s.isDisabled(when))
            return false;
        return !p.isOccupied(s);
    }

    /**
     * Checks the capacity left in the standing area indicated
     *
     * @param a Area to check in
     * @param n Number of requested positions
     * @return True if there are n positions or more left
     */
    public boolean availableCapacity(Standing a, int n) {
        PerformanceArea p = myAreasPositions.get(a);
        if (p == null) {
            return false;
        }
        return p.getAvailablePositons() >= n;
    }

    /**
     * This method tries to get n seats which are together based on heuristics.
     *
     * @param area Area where tickets are requested
     * @param n    Number of tickets requested
     * @param h    Heuristic to be considered
     * @return Set of the requested seats. Null in case the system couldnt find
     *         adecuate seats
     */
    public Set<Seat> getSeats(Sitting area, int n, Heuristic h) {
        int a, b, aThird, valStart;
        aThird = (area.getRows() + 1) / 3;
        switch (h) {
        case CENTERED_LOW:
            a = 1;
            valStart = a;
            b = a + aThird;
            break;
        case CENTERED_MID:
            a = b = area.getRows() / 2;
            valStart = (area.getRows() + 1) / 2;
            a -= (aThird - 1) / 2;
            b += (aThird + 1) / 2;
            break;
        case CENTERED_HIGH:
            b = area.getRows();
            valStart = b;
            a = b - aThird;
            break;
        default:
            // As this method is unique to a PerformanceSitting, and we know that we will
            // have a PerformanceSitting instance, we can cast
            return ((PerformanceSitting) myAreasPositions.get(area)).getFarSeatsNearSet(n);
        }

        return calculateCenteredSeats(area, a, valStart, b, n);
    }

    /**
     * This private method will divide the sitting area indicated into three
     * horizontal parts. Then, it will retrieve n available centered seats
     * 
     * @param s   Area to consider
     * @param aux Auxiliar variable indicating the positions where seats should be
     *            looked in
     * @param n   Number of seats to be bought
     * @return Set of seats. null if cannot find seats
     */
    private Set<Seat> calculateCenteredSeats(Sitting s, int rowFrom, int valStart, int rowTo, int n) {
        Set<Seat> seats = new HashSet<>();
        PerformanceArea p = myAreasPositions.get(s);
        int i, j;

        int middle = (s.getCols() + 1) / 2; // ceil the columns
        i = valStart;
        while (i <= rowTo && i >= rowFrom && seats.size() < n) {
            j = middle;
            while (j > 0 && j <= s.getCols() && seats.size() < n) {
                if (!p.isOccupied(s.getSeat(i, j))) {
                    seats.add(s.getSeat(i, j));
                }
                /**
                 * The counter is performed this way so the seats selected are mostly in the
                 * middle
                 */
                if (j > middle)
                    j -= 2 * (j - middle);
                else
                    j += 2 * (middle - j) + 1;

            }

            if (i > valStart) {
                i -= 2 * (i - valStart);
                if (i < rowFrom)
                    i += 2 * (valStart - i) + 1;
            } else {
                i += 2 * (valStart - i) + 1;
                if (i > rowTo)
                    i -= 2 * (i - valStart);
            }
        }
        if (seats.size() < n)
            return null;

        return seats;
    }

    /**
     * Calculates the revenue of an area in a performance
     *
     * @param area Area to be calculateCenteredd
     * @return Total revenue
     */
    public double getRevenue(NonComposite area) {
        double total = 0.0;
        if (status == PerformanceStatus.CANCELLED) // Performance cancelled => NO revenue
            return 0.0;
        List<Operation> op = opers.get(area);
        if (op == null)
            return total;
        for (Operation o : op) {
            total += o.getRevenue();
        }
        return total;
    }

    /**
     * Changes performance status to cancelled. Status cannot be changed back and no
     * changes can be performed in the performance
     */
    public void cancelPerformance() {
        if (getStatus() != PerformanceStatus.EXPIRED) {
            status = PerformanceStatus.CANCELLED;
            for (List<Operation> op : opers.values()) {
                for (Operation o : op) {
                    o.notifyCustomers(status);
                }
            }
        }
    }

    /**
     * Postpones a performance to a new date
     * 
     * @param newDate Date to be changed to
     */
    public void postponePerformance(LocalDateTime newDate) {
        if (getStatus() != PerformanceStatus.EXPIRED && getStatus() != PerformanceStatus.CANCELLED) {
            when = newDate;
            status = PerformanceStatus.POSTPONED;
            for (List<Operation> op : opers.values()) {
                for (Operation o : op) {
                    o.notifyCustomers(status);
                }
            }
        }
    }

    /**
     * @return Present status of a performance
     */
    public PerformanceStatus getStatus() {
        if (when.isBefore(LocalDateTime.now()) && status != PerformanceStatus.CANCELLED)
            status = PerformanceStatus.EXPIRED;
        return status;
    }

    /**
     * Allows a customer to enter a waiting list to be notified
     * 
     * @param c Customer to add
     * @return True if customer was added
     */
    public boolean enterWaitingList(Customer c) {
        if (numSoldOut < myAreasPositions.size())
            return false;
        if (getStatus() != PerformanceStatus.EXPIRED && getStatus() != PerformanceStatus.CANCELLED) {
            wl.addCustomer(c);
            return true;
        }
        return false;
    }

    /**
     * @return True if it is sold out
     */
    public boolean isSoldOut() {
        return numSoldOut == myAreasPositions.size();
    }

    /**
     * Occupies a list of positions in a performance
     *
     * @param area Area to be occupied in
     * @param ls   List of positions to be unoccupied
     * @return True if positions were occupied succesfully.
     */
    public boolean occupy(NonComposite area, List<Position> ls) {
        if (getStatus() == PerformanceStatus.EXPIRED || getStatus() == PerformanceStatus.CANCELLED)
            return false;
        /* Checking is done inside method occupy */
        if (myAreasPositions.get(area).getAvailablePositons() < ls.size())
            return false;
        boolean aux = myAreasPositions.get(area).occupy(List.copyOf(ls));
        if (myAreasPositions.get(area).getAvailablePositons() == 0)
            numSoldOut += 1;
        return aux;
    }

    /**
     * Unoccupies positions in an area. Additionally, notifies users in the waiting
     * list of the area
     * 
     * @param area Area to unoccupy positions in
     * @param ls   List of positions to be unoccipied
     */
    public void unoccupy(NonComposite area, List<Position> ls) {
        if (getStatus() != PerformanceStatus.EXPIRED && getStatus() != PerformanceStatus.CANCELLED) {
            if (numSoldOut == myAreasPositions.size()) {
                numSoldOut -= 1;
                wl.notifyCustomers(ls.size());
            }

            myAreasPositions.get(area).unoccupy(List.copyOf(ls));
        }
    }

    /**
     * @return Date for the performance
     */
    public LocalDateTime getDate() {
        return when;
    }

    /**
     * @return Event associated to the performance
     */
    public Event getEvent() {
        return eventAssoc;
    }

    /**
     * Associates an operation to a performance
     *
     * @param c  Non composite area
     * @param op Operation to be added
     */
    public void addOperation(NonComposite c, Operation op) {
        opers.putIfAbsent(c, new LinkedList<>());
        opers.get(c).add(op);
    }

    /**
     * Shows occupancy of a certain area. E.g. attendance
     *
     * @param area Area to show
     * @return String cointaining the information
     */
    public String getAttendance(NonComposite area) {
        return myAreasPositions.get(area).toString();
    }

    /**
     * Shows occupancy of a certain area. E.g. attendance
     *
     * @param area Area to show
     * @return String cointaining the information
     */
    public double getAttendanceNum(NonComposite area) {
        return myAreasPositions.get(area).getOccupiedPossitions();
    }

    /**
     * @return How a performance is printed
     */
    @Override
    public String toString() {
        return "Performance of event \"" + this.getEvent().getName() + "\" on date " + this.getDate().toString()
                + (this.getStatus() == PerformanceStatus.CANCELLED ? " - CANCELLED" : "");
    }

    /**
     * Updates an area where seat s has been disable
     * 
     * @param area Area to disable
     * @param s    Seat to be disabled
     */
    public void seatHasBeenDisabled(Sitting area, Seat s) {
        PerformanceArea pa = myAreasPositions.get(area);
        if (pa instanceof PerformanceSitting) { // should always be true
            ((PerformanceSitting) pa).seatHasBeenDisabled(s);
        }
    }

    /**
     * @param area Sitting area to be considered
     * @return A matrix of integers where 1 indicates available and 0 indicates
     *         occupied and -1 disabled
     */
    public int[][] matrixOccupancy(Sitting area) {
        return ((PerformanceSitting) myAreasPositions.get(area)).matrixOccupancy();
    }
}
