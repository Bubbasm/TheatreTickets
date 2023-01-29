package model.operations.payments;

import model.acts.performances.*;
import model.app.TheatreTickets;
import model.areas.NonComposite;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Class for annual passes
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AnnualPass extends Pass {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Set of performances bought */
    private Set<Performance> boughtPerformances;
    /** year of purchase */
    private int year;

    /**
     * Constructor
     * 
     * @param area area associated to the pass
     */
    public AnnualPass(NonComposite area) {
        super(area);
        this.year = LocalDateTime.now().getYear();
        this.boughtPerformances = new HashSet<>();
    }

    /**
     * Getter for the year
     * 
     * @return the year of the pass
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Getter for the price of the pass
     * 
     * @return the price of the pass
     */
    public double getPrice() {
        return TheatreTickets.getInstance().getPassPrice(this.getArea());
    }

    /**
     * Marks a performance as bought
     * 
     * @param p the performance to mark as bought
     * @return true if the performance wasnt already bought, false otherwise
     */
    @Override
    public boolean markAsBought(Performance p) {
        if (p.getDate().getYear() != this.getYear()) {
            return false;
        }
        return this.boughtPerformances.add(p);
    }

    /**
     * Marks a performance as not bought
     * 
     * @param p the performance to unmark as bought
     * @return true if the performance has been unmarked succesfully
     */
    @Override
    public boolean unmarkAsBought(Performance p) {
        return this.boughtPerformances.remove(p);
    }

    /**
     * Check if an event has been bought with the pass
     * 
     * @param p performance to check
     * @return true if it has been bought, false otherwise
     */
    @Override
    public boolean isBought(Performance p) {
        return this.boughtPerformances.contains(p);
    }

    /**
     * Checks if a performance can be bought with this pass
     * 
     * @param p Performance to buy
     * @return True if it can be bought
     */
    @Override
    public boolean isValidToUse(Performance p) {
        if (getYear() < p.getDate().getYear())
            return false; /* Expired */
        if (isBought(p))
            return false; /* This performance has already been bought */
        return true;
    }

    /**
     * Return the revenue made by the performance indicated. Calculated taking into
     * account the weigth of the performance over all the prices
     * 
     * @param p Peroformance to consider
     * @return Revenue
     */
    public double getRevenue(Performance p) {
        double thisPerfPrice = p.getEvent().getPrice(this.getArea());
        double sumOfAll = 0.0;
        for (Performance perf : boughtPerformances) {
            /** This variable accounts the total price of all assisted performances */
            sumOfAll += perf.getEvent().getPrice(this.getArea());
        }
        /**
         * This formula is being used so the excess/loss from the assistance to
         * performances is added up proportionally to the perfromance price
         */
        return thisPerfPrice + thisPerfPrice / sumOfAll * (this.getPrice() - sumOfAll);

    }
}
