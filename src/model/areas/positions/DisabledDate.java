package model. areas.positions;

import java.io.Serializable;
import java.time.*;

/**
 * Class that holds 2 dates. A seat will be disabled in between those dates
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class DisabledDate implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** date from */
    private LocalDateTime from;
    /** date to */
    private LocalDateTime to;

    /**
     * Constructor
     * 
     * @param from initial date
     * @param to   final date
     */
    public DisabledDate(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Getter for the initial date
     * 
     * @return the initial date
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Setter for the initial date
     * 
     * @param f date to be assigned
     */
    public void setFrom(LocalDateTime f) {
        this.from = f;
    }

    /**
     * Getter for the final date
     * 
     * @return the final date
     */
    public LocalDateTime getTo() {
        return this.to;
    }

    /**
     * Setter for the final date
     * 
     * @param t date to be assigned
     */
    public void setTo(LocalDateTime t) {
        this.to = t;
    }
}
