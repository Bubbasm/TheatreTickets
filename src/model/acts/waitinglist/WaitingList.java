package model. acts.waitinglist;

import java.util.Set;
import java.io.Serializable;
import java.util.HashSet;

import model. users.notifications.*;
import model. users.*;
import model. acts.performances.*;

/**
 * Waiting list for customers
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class WaitingList implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Set of customers that want to be notified */
    private Set<Customer> waiting = new HashSet<>();
    /** Peformance associated to the waiting list */
    private Performance p;

    /**
     * Constructor for waiting list
     * 
     * @param p Performance associated
     */
    public WaitingList(Performance p) {
        this.p = p;
    }

    /**
     * Adds a customer to the waiting list
     * 
     * @param c Customer to add
     */
    public void addCustomer(Customer c) {
        waiting.add(c);
    }

    /**
     * Removes customer from waiting list
     * 
     * @param c Customer to be removed
     */
    public void removeCustomer(Customer c) {
        waiting.remove(c);
    }

    /**
     * Notifies all customers in waiting list and empties it
     * 
     * @param availPositions Number of positions that will be made available
     */
    public void notifyCustomers(int availPositions) {
        for (Customer c : waiting) {
            c.addNotification(new AvailableNotification(p, availPositions));
            c.removeFromWaitingList(p);
        }
        // In case the waiting list must be empied we just create a new one so the
        // Garbage collector removes the previous one
        waiting = new HashSet<>();
    }
}
