package model. users.notifications;

import model. acts.performances.Performance;

/**
 * class is for notifications that indicate the availability of ...
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AvailableNotification extends Notification {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** number of available positions */
    private int availPositions;

    /**
     * Constructor
     * 
     * @param p         performance that now has available capacity
     * @param available number of available positions
     */
    public AvailableNotification(Performance p, int available) {
        super(p);
        this.availPositions = available;
    }

    /**
     * Method to print the notification
     * 
     * @return the string with the information
     */
    public String toString() {
        return "There are now " + availPositions + " tickets left for the performance "
                + this.getPerformance().getEvent().getTitle() + " on " + this.getPerformance().getDate();
    }
}
