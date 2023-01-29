package model. operations.payments;

import model. areas.NonComposite;
import model. acts.performances.*;

/**
 * Abstract class that represents the passes (Annual/Cycle)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class Pass extends PaymentMethod {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** unique id of the pass */
    public int id;
    /** counter for new ids */
    public static int counter = 1;
    /** area for which the pass is bought */
    public NonComposite area;

    /**
     * Constructor
     * 
     * @param area area associated to the pass
     */
    public Pass(NonComposite area) {
        this.id = counter;
        this.area = area;
        counter++;
    }

    /**
     * Getter for the pass' area
     * 
     * @return the area of the pass
     */
    public NonComposite getArea() {
        return this.area;
    }

    /**
     * Getter for the pass' price
     * 
     * @return the price of the pass
     */
    public abstract double getPrice();

    /**
     * Checks if a performance can be bought with this pass
     * 
     * @param p Performance to buy
     * @return True if it can be bought
     */
    public abstract boolean isValidToUse(Performance p);

    /**
     * Return the revenue made by the performance indicated. Calculated taking into
     * account the weigth of the performance over all the prices
     * 
     * @param p Peroformance to consider
     * @return Revenue
     */
    public abstract double getRevenue(Performance p);

    /**
     * Check if an performance has been bought with the pass
     * 
     * @param p performance to check
     * @return true if it has been bought, false otherwise
     */
    public abstract boolean isBought(Performance p);

    /**
     * Marks a performance as bought
     * 
     * @param p performance to buy
     * @return true if it has been marked succesfully
     */
    public abstract boolean markAsBought(Performance p);

    /**
     * Marks a performance as not bought
     * 
     * @param p the performance to unmark as bought
     * @return true if the performance has been unmarked succesfully
     */
    public abstract boolean unmarkAsBought(Performance p);
}
