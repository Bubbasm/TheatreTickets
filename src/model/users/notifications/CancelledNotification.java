package model. users.notifications;

import model. acts.performances.Performance;

/**
 * This class is for notifications of Performance cancellations...
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CancelledNotification extends Notification {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Money to return */
    private double money;

    /**
     * Constructor
     * 
     * @param p     performance that has been cancelled
     * @param money Money to be refund for the cancellation
     */
    public CancelledNotification(Performance p, double money) {
        super(p);
        this.money = money;
    }

    /**
     * Method to print the notification
     * 
     * @return the string with the information
     */
    public String toString() {
        return "The performance " + this.getPerformance().getEvent().getTitle() + " has been cancelled. "
                + (this.money > 0 ? this.money + "€ have been refunded." : "");
    }
}
