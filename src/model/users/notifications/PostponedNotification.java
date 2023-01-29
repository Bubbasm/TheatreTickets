package model. users.notifications;

import java.time.LocalDateTime;

import model. acts.performances.Performance;

/**
 * This class is for notifications of Performances getting postponed
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PostponedNotification extends Notification {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** new date for performance */
    private LocalDateTime newDate;

    /**
     * Constructor
     * 
     * @param p performance that has been postponed
     */
    public PostponedNotification(Performance p) {
        super(p);
        newDate = p.getDate();
    }

    /**
     * Method to print the notification
     * 
     * @return the string with the information
     */
    public String toString() {
        return "The performance " + this.getPerformance().getEvent().getTitle() + " has been postponed. The new date is "
                + this.newDate;
    }

}
