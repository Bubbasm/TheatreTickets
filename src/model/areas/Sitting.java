package model. areas;

import model. areas.positions.*;
import java.time.*;

/**
 * Class for the sitting type areas. Child of the class NonComposite.
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Sitting extends NonComposite {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** number of rows */
    private int nRows;
    /** number of columns */
    private int nCols;
    /** matrix of seats */
    private Seat seats[][];

    /**
     * Constructor
     * 
     * @param name name of the area
     * @param r    number of rows
     * @param c    number of columns
     */
    public Sitting(String name, int r, int c) {
        super(name);
        this.nRows = r;
        this.nCols = c;
        this.seats = new Seat[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                this.seats[i][j] = new Seat(i + 1, j + 1, this);
            }
        }
    }

    /**
     * Method that returns the capacity of the area
     * 
     * @return an integer with the capacity
     */
    @Override
    public int getCapacity() {
        return this.nRows * this.nCols;
    }

    /**
     * Getter for the number of rows
     * 
     * @return number of rows
     */
    public int getRows() {
        return this.nRows;
    }

    /**
     * Getter for the number of columns
     * 
     * @return number of columns
     */
    public int getCols() {
        return this.nCols;
    }

    /**
     * Returns the seat in position (r, c)
     * 
     * @param r row
     * @param c column
     * @return the seat in position (r, c)
     */
    public Seat getSeat(int r, int c) {
        return this.seats[r - 1][c - 1];
    }

    /**
     * Check if a seat is disabled in an specified date
     * 
     * @param r    row
     * @param c    column
     * @param date date to check
     * @return true if the seat is disabled that date, false otherwise
     */
    public boolean isDisabled(int r, int c, LocalDateTime date) {
        return this.getSeat(r, c).isDisabled(date);
    }

    /**
     * Disable a seat between two dates
     * 
     * @param r    row
     * @param c    column
     * @param from starting date
     * @param to   ending date
     * @return true if disable operation was succesful
     */
    public boolean disable(int r, int c, LocalDateTime from, LocalDateTime to) {
        return this.getSeat(r, c).disable(from, to);
    }

    
    /**
     * Getter for matrix ocupancy
     * 
     * @return 1 indicates not disabled. -1 indicates disabled
     */
    public int[][] matrixOccupancy(){
        int[][] ret = new int[getRows()][getCols()];
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if(getSeat(i+1, j+1).isDisabled()){
                    ret[i][j] = -1;
                }else{
                    ret[i][j] = 1;
                }
            }
        }
        return ret;
    }

    /**
     * Method to print the area information
     * 
     * @return the String with the info
     */
    @Override
    public String toString() {
        return super.toString() + ", type: Sitting(" + this.getRows() + "x" + this.getCols() + ")";
    }
}
