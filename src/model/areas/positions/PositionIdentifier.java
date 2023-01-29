package model. areas.positions;

import model. areas.*;

/**
 * Class that represents a seat but for standing areas
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class PositionIdentifier extends Position {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** private attributes */
    private Standing area;

    /**
     * Constructor
     * 
     * @param a Number of the position
     */
    public PositionIdentifier(Standing a) {
        this.area = a;
    }

    /**
     * Getter for the Area
     * 
     * @return the area
     */
    @Override
    public Standing getArea() {
        return this.area;
    }

    /**
     * Method to display the info as a string
     * 
     * @return the string with the info
     */
    @Override
    public String toString() {
        return "Position in area \"" + this.area.getName() + "\"";
    }

    /**
     * Return a string that will be displayed in the PDF (less info than toString)
     * 
     * @return the string that will be displayed
     */
    @Override
    public String getTicketMsg() {
        return super.getTicketMsg() + " (Standing area, no seat assigned)";
    }
}
