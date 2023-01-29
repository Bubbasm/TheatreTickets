package model.acts.performances.organization;

import java.util.List;

import model.areas.*;
import model.areas.positions.*;

/**
 * This is a class destined to account for how many positions have been occupied
 * As it is standing and position doesn't exist, the position is ignored
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceStanding extends PerformanceArea {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Counter of all occupied positions */
    private int occupiedCounter;

    /**
     * Constructor for a standing area accountant
     * 
     * @param a        Area to consider
     * @param realCapp Real capacity of the area
     */
    public PerformanceStanding(Standing a, int realCapp) {
        super(a, realCapp);
        this.occupiedCounter = 0;
    }

    /**
     * Occupies a list of positions
     *
     * @param pos List of position to be unoccupied. Only the size of the list
     *            matters
     * @return True if they could be occupied
     */
    public boolean occupy(List<Position> pos) {
        if (this.getAvailablePositons() < pos.size())
            return false;
        this.occupiedCounter += pos.size();
        return true;
    }

    /**
     * Occupies a certain seat in a performance.
     * 
     * @param p Position to be occupied. Ignored
     */
    public boolean occupy(Position p) {
        if (this.getAvailablePositons() > 0) {
            this.occupiedCounter++;
            return true;
        }
        return false;
    }

    /**
     * Unoccupies a certain seat in the performnce
     * 
     * @param p Position to be unoccupied. Ignored
     */
    public boolean unoccupy(Position p) {
        if (this.getOccupiedPossitions() > 0) {
            this.occupiedCounter--;
            return true;
        }
        return false;
    }

    /**
     * Unoccipies a list of positions
     *
     * @param pos Positions to be unoccupied. Can be a list of null, as position is
     *            ignored
     * @return True in success
     *
     */
    public boolean unoccupy(List<Position> pos) {
        if (pos.size() > this.getOccupiedPossitions()) {
            return false;
        }
        this.occupiedCounter -= pos.size();
        return true;
    }

    /**
     * @param p Ignored
     * @return True if seat is occupied. False otherwise
     */
    public boolean isOccupied(Position p) {
        if (this.getAvailablePositons() == 0)
            return true;
        return false;
    }

    /**
     * @return Number of seats available in the area associated
     */
    public int getAvailablePositons() {
        return super.associated.getCapacity() - this.occupiedCounter - super.restric;
    }

    /**
     * @return Number of seats occupied in the area associated
     */
    public int getOccupiedPossitions() {
        return this.occupiedCounter;
    }
}
