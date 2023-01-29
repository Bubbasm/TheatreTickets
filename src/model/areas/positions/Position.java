package model. areas.positions;

import java.io.Serializable;

import model. areas.NonComposite;

/**
 * Abstract class, parent of Seats and PositionNumber
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public abstract class Position implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /**
     * Abstract getter for the Area
     * 
     * @return the area
     */
    abstract public NonComposite getArea();

    /**
     * Method to display the info as a string
     * 
     * @return the string with the info
     */
    abstract public String toString();

    /**
     * Return a string that will be displayed in the PDF (less info than toString)
     * 
     * @return the string that will be displayed
     */
    public String getTicketMsg() {
        return "Area: \"" + this.getArea().getName() + "\"";
    }
}
