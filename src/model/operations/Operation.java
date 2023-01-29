package model.operations;

import model.acts.performances.*;
import model.app.TheatreTickets;
import model.areas.*;
import model.areas.positions.*;
import model.enums.PerformanceStatus;
import es.uam.eps.padsof.telecard.*;
import model.exceptions.CreationException;
import model.users.notifications.*;
import model.operations.payments.*;
import model.users.*;

import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Abstract Class that represents an operation (purchase/reservation)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public abstract class Operation implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Customer associated */
    private Customer customer;
    /** performance associated */
    private Performance performance;
    /** set of tickets of this operation */
    private Set<Ticket> tickets;
    /** payment methods used to pay */
    private Set<PaymentMethod> pms;
    /** postions occupied for this operation */
    private List<Position> positions;
    /** unique if for operation */
    private int id;

    /**
     * Constructor
     * 
     * @param customer  customer that made the operation
     * @param perf      performance to be bought/reserved
     * @param positions positions to be bought/reserved
     * @throws CreationException If any invalid parameter
     */
    public Operation(Customer customer, Performance perf, List<Position> positions) throws CreationException {
        this.customer = customer;
        this.performance = perf;
        this.positions = positions;
        this.id = TheatreTickets.getInstance().getLastOperationID();
        this.tickets = new HashSet<>();
        boolean allAreFromTheSameArea = true;
        NonComposite a = positions.get(0).getArea();
        for (Position p : positions) {
            allAreFromTheSameArea = allAreFromTheSameArea && a.equals(p.getArea());
        }

        if (!allAreFromTheSameArea) {
            throw new CreationException("Every position must be on the same area");
        } else {
            perf.occupy(a, positions);
        }
        perf.addOperation(a, this);
        TheatreTickets.getInstance().incrementOperationID();
    }

    /**
     * Getter for the customer attribute
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Getter for the tickets
     * 
     * @return the tickets
     */
    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    /**
     * Getter for positions
     * 
     * @return the positions
     */
    public List<Position> getPositions() {
        return this.positions;
    }

    /**
     * Getter for the performance attribute
     * 
     * @return the performance
     */
    public Performance getPerformance() {
        return this.performance;
    }

    /**
     * Getter for the id attribute
     * 
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the area of operation
     * 
     * @return The area
     */
    public NonComposite getArea() {
        return positions.get(0).getArea();
    }

    /**
     * Getter for the payment method
     * 
     * @return the payment method
     */
    public Set<PaymentMethod> getPayments() {
        return this.pms;
    }

    /**
     * Setter for the payment methods. The methods passed are valid for the
     * performance (checked outside)
     * 
     * @param pms payment methods, multiple passes allowed, but only one credit card
     *            (this will be controlled in the GUI)
     */
    public void setPayments(Set<PaymentMethod> pms) {
        this.pms = pms;
    }

    /**
     * Getter for the price of the operation
     * 
     * @return the price of the purchase
     */
    public double getPrice() {
        double price = 0;
        for (Ticket t : this.getTickets()) {
            price += t.getPrice();
        }
        return price;
    }

    /**
     * Getter for the price of the operation
     * 
     * @return the price of a generic ticket
     */
    public double getPricePerTicket() {
        return performance.getEvent().getPrice(positions.get(0).getArea());
    }

    /**
     * Getter for the revenue that the app has made through this operation
     * 
     * @return the revenue
     */
    public double getRevenue() {
        double revenue = 0;
        for (Ticket t : this.getTickets()) {
            revenue += t.getRevenue();
        }
        return revenue;
    }

    /**
     * Generates the corresponding tickets after the payment has been completed
     * 
     * @throws OrderRejectedException Order was rejected
     */
    public void generateTickets() throws OrderRejectedException {
        PaymentMethod cc = null; // Credit card
        List<Position> pos = new ArrayList<>(this.getPositions()); // we copy the list as we are removing elements from
                                                                   // it
        for (PaymentMethod p : this.getPayments()) {
            if (p instanceof CreditCard) {
                cc = p;
            } else {
                this.tickets.add(new Ticket(pos.remove(0), this, p));
            }
            if (pos.isEmpty()) {
                break; // in theory pos will never be empty before the payment methods run out, this is
                       // here just in case
            }
        }
        for (Position p : pos) {
            // cc wont be null because if we entered this loop is because
            // we had less payment methods than tickets to buy, and
            // one credit card has been provided
            this.tickets.add(new Ticket(p, this, cc));
        }
        this.printTickets();
    }

    /**
     * Notifies the customer and adjusts payment methods to their previous state
     * Tested in PerformanceTest since it is used only by performance (when it gets
     * cancelled/postponed)
     * 
     * @param ps Performance status as reason of notification
     */
    public void notifyCustomers(PerformanceStatus ps) {
        if (ps == PerformanceStatus.CANCELLED) {
            for (Ticket t : tickets) {
                if (t.getPayment() instanceof CreditCard) {
                    try {
                        ((CreditCard) t.getPayment()).deposit(t.getPrice(),
                                "Event \"" + t.getEventName() + "\" cancelled, refund", true);
                    } catch (OrderRejectedException e) {
                        System.err.println("Couldn't refund money for \"" + t.getEventName() + "\" to Customer "
                                + this.getCustomer().getUsername());
                    }
                } else {
                    ((Pass) t.getPayment()).unmarkAsBought(t.getPerformance());
                }
            }
            this.getCustomer().addNotification(new CancelledNotification(this.getPerformance(), this.getPrice()));
        } else if (ps == PerformanceStatus.POSTPONED) {
            this.getCustomer().addNotification(new PostponedNotification(this.getPerformance()));
        }
    }

    /**
     * Prints the pdf files that correspond to the tickets
     */
    public void printTickets() {
        try {
            for (Ticket t : this.getTickets()) {
                t.print();
            }
        } catch (Exception e) {
            System.err.println("Error printing the tickets");
        }
    }
}
