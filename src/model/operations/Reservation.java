package model.operations;

import model.users.*;
import model.areas.*;
import model.areas.positions.*;
import model.operations.payments.*;
import model.acts.performances.*;
import model.app.TheatreTickets;
import model.enums.*;
import es.uam.eps.padsof.telecard.*;
import model.exceptions.CreationException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.time.format.*;

/**
 * Class that represents a reservation
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Reservation extends Operation {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** reservation date */
    private LocalDateTime reservationDate;
    /** reservation status */
    private ReservationStatus status;

    /**
     * Constructor
     * 
     * @param customer  customer that made the reservation
     * @param perf      performance that is associated to this reservation
     * @param positions positions requested
     * @throws CreationException If any invalid parameter
     */
    public Reservation(Customer customer, Performance perf, List<Position> positions) throws CreationException {
        super(customer, perf, positions);
        this.reservationDate = LocalDateTime.now();
        this.status = ReservationStatus.ACTIVE;
    }

    /**
     * Constructor that allows to change the date of the reservation (TESTING
     * PURPOSES ONLY)
     * 
     * @param customer  customer that made the reservation
     * @param perf      performance that is associated to this reservation
     * @param positions positions requested
     * @param d         date to simulate
     * @throws CreationException If any invalid parameter
     */
    public Reservation(Customer customer, Performance perf, List<Position> positions, LocalDateTime d)
            throws CreationException {
        super(customer, perf, positions);
        this.reservationDate = d;
        this.status = ReservationStatus.ACTIVE;
    }

    /**
     * Getter for the Status of the reservation
     * 
     * @return the status
     */
    public ReservationStatus getStatus() {
        isExpired();
        return this.status;
    }

    /**
     * Check if the reservation has expired and, in that case, set it as expired
     * 
     * @return true if the reservation has expired
     */
    public boolean isExpired() {
        boolean ret = LocalDateTime.now().isAfter(this.getExpiringDate());
        if (ret) {
            this.expire();
            return ret;
        }
        return ret;
    }

    /**
     * Getter for the date of the reservation
     * 
     * @return the date in which the reservation was made
     */
    public LocalDateTime getDate() {
        return this.reservationDate;
    }

    /**
     * Getter for the expiring date
     * 
     * @return the date on which the reservation expires.
     */
    public LocalDateTime getExpiringDate() {
        int hours = TheatreTickets.getInstance().getHoursForReservation();
        LocalDateTime limitForReservation = this.getPerformance().getDate().minusHours(hours);
        LocalDateTime maxExpiringDate = this.getDate().plusHours(hours);
        if (limitForReservation.isBefore(maxExpiringDate)) {
            return limitForReservation;
        } else {
            return maxExpiringDate;
        }
    }

    /**
     * Confirm and pay a reservation with the payment method provided
     * 
     * @param pms payment method
     * @throws OrderRejectedException Order was rejected
     */
    public void confirm(Set<PaymentMethod> pms) throws OrderRejectedException {
        this.isExpired();
        if (this.status == ReservationStatus.ACTIVE) {
            this.status = ReservationStatus.CONFIRMED;
            this.setPayments(pms);
            this.generateTickets();
        }
    }

    /**
     * Mark the reservation as cancelled and unoccupy the seats
     */
    public void cancel() {
        if (this.status == ReservationStatus.ACTIVE) {
            this.status = ReservationStatus.CANCELLED;
            this.unnocupy();
        }
    }

    /**
     * Mark the reservation as expired and unoccupy the seats
     */
    public void expire() {
        if (this.status == ReservationStatus.ACTIVE) {
            this.status = ReservationStatus.EXPIRED;
            this.unnocupy();
        }
    }

    /**
     * Getter for the number of tickets
     * 
     * @return number of tickets
     */
    public int getNumberOfTickets() {
        return this.getPositions().size();
    }

    /**
     * Private method that unnocupies the seats/positions of this reservation
     */
    private void unnocupy() {
        NonComposite a = this.getPositions().get(0).getArea(); // get the area of one of the tickets
        this.getPerformance().unoccupy(a, this.getPositions());
    }

    /**
     * Getter for the revenue
     * 
     * @return 0 if the reservation isnt confirmed, the revenue otherwise
     */
    @Override
    public double getRevenue() {
        return (this.getStatus() == ReservationStatus.CONFIRMED ? super.getRevenue() : 0);
    }

    /**
     * Additionally from notifying customers, personal parameters are updated
     * 
     * @param ps Performance status as reason of notification
     */
    @Override
    public void notifyCustomers(PerformanceStatus ps) {
        super.notifyCustomers(ps);
        if (this.status == ReservationStatus.ACTIVE) {
            if (ps.equals(PerformanceStatus.CANCELLED))
                this.status = ReservationStatus.CANCELLED;
        }
    }

    /**
     * Getter for the date string
     *
     * @param ldt Date
     * @return The string
     */
    private String date(LocalDateTime ldt) {
        return ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    /**
     * Getter for the toString
     * 
     * @return The string
     */
    @Override
    public String toString() {
        return "Reservation for " + getPerformance().getEvent().getTitle() + " at " + date(getPerformance().getDate())
                + (isExpired() ? " is expired" : " expires in " + date(getExpiringDate()));
    }
}
