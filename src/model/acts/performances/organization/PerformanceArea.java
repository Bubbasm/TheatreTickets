package model.acts.performances.organization;

import java.io.Serializable;
import java.util.List;
import model.areas.*;
import model.areas.positions.*;

/**
 * This is an abstract class destined to store all occupied seats or positions
 * in an area
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class PerformanceArea implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Area asociated with positions stored */
    protected NonComposite associated;
    /** Number of positions occupied after restriction applies */
    protected int restric;

    /**
     * Constructor for PerformanceArea
     * 
     * @param a            Area
     * @param realCapacity Real capacity of the area
     */
    public PerformanceArea(NonComposite a, int realCapacity) {
        this.associated = a;
        restric = a.getCapacity() - realCapacity;
    }

    /**
     * @param a Area to compare to
     * @return True if area asociated is the same as passed
     */
    public boolean isOfArea(NonComposite a) {
        /*
         * As all areas will be pointers to the same area, we can just check if they are
         * equal (pointing to same object) instead of same (identical components value)
         */
        return associated.equals(a);
    }

    /**
     * Occupies a list of positions
     *
     * @param pos List of positions to be occupied
     * @return True if all positions could be occupied
     */
    public abstract boolean occupy(List<Position> pos);

    /**
     * Occupies a certain position in the performance
     * 
     * @param p Position to be occupied
     * @return true if succesful
     */
    public abstract boolean occupy(Position p);

    /**
     * Unoccupies a certain position in the performnce
     * 
     * @param p Position to unoccupy
     * @return true if succesful
     */
    public abstract boolean unoccupy(Position p);

    /**
     * Unoccupies a list of positions
     *
     * @param pos List of positions to be unoccupied
     * @return True if unoccupied succesfully
     */
    public abstract boolean unoccupy(List<Position> pos);

    /**
     * @param p Position to be judged
     * @return True if position is occupied. False otherwise
     */
    public abstract boolean isOccupied(Position p);

    /**
     * @return Number of positions available in the area associated
     */
    public abstract int getAvailablePositons();

    /**
     * @return Number of positions occupied in the area associated
     */
    public abstract int getOccupiedPossitions();

    /**
     * @return True if there are no more positions left
     */
    public boolean isSoldOut() {
        return getAvailablePositons() == 0;
    }

    /**
     * @return Printing the occupancy of an area
     */
    @Override
    public String toString() {
        return "There are " + getOccupiedPossitions() + " out of " + (getOccupiedPossitions() + getAvailablePositons())
                + " positions occupied. " + this.restric + " restricted positions.";
    }

}
