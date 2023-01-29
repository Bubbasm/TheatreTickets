package model.operations.payments;

import model.acts.cycles.*;

import java.util.HashSet;
import java.util.Set;

import model.acts.events.Event;
import model.areas.NonComposite;
import model.acts.performances.*;

/**
 * Class for cyclic passes
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CyclePass extends Pass {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Cycle associated */
    private Cycle cycle;
    /** events that have been bought */
    private Set<Event> boughtEvents;

    /**
     * Constructor
     * 
     * @param area  area associated to the pass
     * @param cycle cycle of events for the pass
     */
    public CyclePass(NonComposite area, Cycle cycle) {
        super(area);
        this.cycle = cycle;
        this.boughtEvents = new HashSet<>();
    }

    /**
     * Getter for the cycle
     * 
     * @return the cycle associated to the pass
     */
    public Cycle getCycle() {
        return this.cycle;
    }

    /**
     * Getter for the discount
     * 
     * @return the discount between 0 and 1. Price should be calculated as
     *         price*(1-discount)
     */
    public double getDiscount() {
        return this.getCycle().getDiscount(this.getArea());
    }

    /**
     * Getter for the price of the pass The price is the sum of the prices (with the
     * discount) for each event
     * 
     * @return the price of the pass
     */
    public double getPrice() {
        double price = 0;
        for (Event e : this.getCycle().getEvents()) {
            price += e.getPrice(this.getArea());
        }
        return price * (1 - this.getDiscount());
    }

    /**
     * Getter for the price of the pass The price is the sum of the prices (with the
     * discount) for each event
     * 
     * @param c Cycle of which we want to calculate the price
     * @param a Non composite area for the pass
     * @return the price of the pass
     */
    public static double getPrice(Cycle c, NonComposite a) {
        double price = 0;
        for (Event e : c.getEvents()) {
            price += e.getPrice(a);
        }
        return price * (1 - c.getDiscount(a));
    }

    /**
     * Marks the performance's event as bought
     * 
     * @param p performance to buy
     * @return true if the performance's event wasnt already bought, false otherwise
     */
    @Override
    public boolean markAsBought(Performance p) {
        if (!this.getCycle().getEvents().contains(p.getEvent())) {
            return false;
        }
        return this.boughtEvents.add(p.getEvent());
    }

    /**
     * Marks a performance as not bought
     * 
     * @param p the performance to unmark as bought
     * @return true if the performance has been unmarked succesfully
     */
    @Override
    public boolean unmarkAsBought(Performance p) {
        return this.boughtEvents.remove(p.getEvent());
    }

    /**
     * Check if an event has been bought with the pass
     * 
     * @param p performance to check
     * @return true if it has been bought, false otherwise
     */
    @Override
    public boolean isBought(Performance p) {
        return this.boughtEvents.contains(p.getEvent());
    }

    /**
     * Checks if a performance can be bought with this pass
     * 
     * @param p Performance to buy
     * @return True if it can be bought
     */
    @Override
    public boolean isValidToUse(Performance p) {
        if (!cycle.getEvents().contains(p.getEvent()))
            return false;
        return !isBought(p);
    }

    /**
     * Return the revenue made by the performance indicated. Calculated taking into
     * account the weigth of the performance over all the prices
     * 
     * @param p Peroformance to consider
     * @return Revenue
     */
    public double getRevenue(Performance p) {
        double sumOfBought = 0, thisPerfPrice = p.getEvent().getPrice(this.getArea()) * (1 - this.getDiscount());
        for (Event e : this.boughtEvents) {
            sumOfBought += e.getPrice(this.getArea()) * (1 - this.getDiscount());
        }
        /**
         * This formula is being used so the excess from the not assisted events is
         * added up proportionally to the perfromance price
         */
        return thisPerfPrice + thisPerfPrice / this.getPrice() * (this.getPrice() - sumOfBought);
    }
}
