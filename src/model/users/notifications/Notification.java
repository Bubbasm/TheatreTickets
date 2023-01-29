package model. users.notifications;

import java.io.Serializable;
import java.time.LocalDateTime;

import model. acts.performances.Performance;

/**
 * Class contains all three types of notifications
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class Notification implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Timestamp of the notification */
    private LocalDateTime timestamp;
    /** Performance who generated this notification */
    private Performance performance;

    /**
     * Constructor
     * 
     * @param performance performance associated to the notification
     */
    public Notification(Performance performance) {
        timestamp = LocalDateTime.now();
        this.performance = performance;
    }

    /**
     * Getter for the performance
     * 
     * @return the performance
     */
    public Performance getPerformance() {
        return this.performance;
    }

    /**
     * Getter for the timestamp
     * 
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Method to print the notification
     * 
     * @return the string with the information
     */
    public abstract String toString();
}
