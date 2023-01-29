package model.areas.positions;

import model.areas.*;
import java.time.*;

/**
 * Class that represents a seat in sitting areas
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Seat extends Position {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** row */
    private int row;
    /** column */
    private int col;
    /** area associated */
    private Sitting area;
    /** dates in which a seat is disabled */
    private DisabledDate dates;

    /**
     * Constructor
     * 
     * @param r    row number (starts at 1)
     * @param c    column number (starts at 1)
     * @param area area in which the seat is located
     */
    public Seat(int r, int c, Sitting area) { /** error checking (negative numbers for example) */
        this.row = r;
        this.col = c;
        this.area = area;
    }

    /**
     * Getter for the row number
     * 
     * @return the row number
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Setter for the row number
     * 
     * @param r number to assign
     */
    public void setRow(int r) {
        this.row = r;
    }

    /**
     * Getter for the column number
     * 
     * @return the column number
     */
    public int getColumn() {
        return this.col;
    }

    /**
     * Setter for the column number
     * 
     * @param c number to assign
     */
    public void setColumn(int c) {
        this.col = c;
    }

    /**
     * Getter for the Area
     * 
     * @return the area
     */
    @Override
    public Sitting getArea() {
        return this.area;
    }

    /**
     * Getter for the disabled dates
     * 
     * @return the dates
     */
    public DisabledDate getDates() {
        return this.dates;
    }

    /**
     * Method that disables a seat
     * 
     * @param from initial date
     * @param to   final date
     * @return true if disabled
     */
    public boolean disable(LocalDateTime from, LocalDateTime to) {
        if (this.getDates() == null) {
            // If it wasn't disabled before
            this.dates = new DisabledDate(from, to);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the seat is disabled on a given date
     * 
     * @param d Date to check
     * @return true if it is disabled, false otherwise
     */
    public boolean isDisabled(LocalDateTime d) {
        if (this.dates == null) {
            return false;
        } else {
            /* if dates.from < d < dates.to then return true; else return false */
            return (dates.getFrom().isBefore(d) && dates.getTo().isAfter(d));
        }
    }

    /**
     * Checks if the seat is disabled from the present to any future
     * 
     * @return true if it is disabled, false otherwise
     */
    public boolean isDisabled() {
        if (this.dates == null) {
            return false;
        }
        LocalDateTime d = LocalDateTime.now();
        /* if d < dates.to then return true; else return false */
        return (dates.getTo().isAfter(d));
    }

    /**
     * Computes manhatan distance to seat
     * 
     * @param s Seat to compare
     * @return distance
     */
    public int distanceTo(Seat s) {
        return Math.abs(this.getRow() - s.getRow()) + Math.abs(this.getColumn() - s.getColumn());
    }

    /**
     * Method to display the info as a string
     * 
     * @return the string with the info
     */
    @Override
    public String toString() {
        return "Seat in area \"" + this.area.getName() + "\", in position (" + this.getRow() + ", " + this.getColumn()
                + ") " + (this.getDates() != null ? "Disabled from " + this.getDates().getFrom().toString() + " to "
                        + this.getDates().getTo().toString() : "");
    }

    /**
     * Return a string that will be displayed in the PDF (less info than toString)
     * 
     * @return the string that will be displayed
     */
    @Override
    public String getTicketMsg() {
        return super.getTicketMsg() + " (row: " + this.getRow() + " column: " + this.getColumn() + ")";
    }
}
