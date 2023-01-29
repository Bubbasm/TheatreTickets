package model.acts.performances.organization;

import java.util.*;

import model.acts.performances.Performance;
import model.areas.*;
import model.areas.positions.*;

/**
 * This is an abstract class destined to store all occupied seats or positions
 * in an area
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceSitting extends PerformanceArea {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** Array of integers representing all seats */
    private int[][] allSeats;
    /** Number of seats disabled */
    private int disabledSeatsCount;
    /** Seats occupied */
    private Set<Seat> occupiedSeats = new HashSet<>();
    /** Performance associated */
    private Performance perf;

    /**
     * Constructor for sitting area accountant
     * 
     * @param a            Area to consider
     * @param realCapacity Actual capacity after restriction
     * @param perf         Performance associated
     */
    public PerformanceSitting(Sitting a, int realCapacity, Performance perf) {
        super(a, realCapacity);
        allSeats = new int[a.getRows()][a.getCols()];
        this.perf = perf;
        initMatrix();
        /** Setting all seats to the maximum value they can possibly have */
    }

    /**
     * Initializes the matrix to proper values.
     */
    private void initMatrix() {
        /** We know it is instanceof Sitting because of the constructor */
        Sitting a = (Sitting) super.associated;
        int restricCont = super.restric;
        int max = Math.max(a.getRows(), a.getCols());
        disabledSeatsCount = 0;

        for (int i = 0; i < a.getRows(); i++) {
            int aux = restricCont / a.getRows();
            int toAux = 0;
            if (restricCont % a.getRows() != 0) {
                restricCont--;
                aux++;
            }
            for (int j = 0; j < a.getCols(); j++) {
                if (aux > 0 && toAux < aux
                        && (j + a.getCols() / (2 * aux) + i * (a.getCols() / (2 * aux))) % (a.getCols() / aux) == 0) {
                    // if there are seats to restrict, is in an distributed position and hasnt
                    // already finished puting disabled seats
                    toAux++;
                    allSeats[i][j] = -2; // restriction application
                } else if (!a.getSeat(i + 1, j + 1).isDisabled(perf.getDate())) {
                    allSeats[i][j] = max;

                } else {
                    allSeats[i][j] = -1; // -1 for disabled seats
                    disabledSeatsCount++;
                }

            }
        }
        // favor those seats which are not separated
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                if (getValidAdjacent(a.getSeat(i + 1, j + 1)) == null && allSeats[i][j] > 1) {
                    allSeats[i][j] -= 1;
                }
            }
        }

    }

    /**
     * Occupies a list of seats
     *
     * @param pos List of seats
     * @return True if all postions could be occupied
     *
     */
    public boolean occupy(List<Position> pos) {
        /** We are making sure all positions are Seats */
        for (Position p : pos) {
            if (!(p instanceof Seat))
                return false;
            else if (p instanceof Seat) {
                /* If any of the seats to be occupied is occupied, we dont occupy any */
                if (isOccupied(p)) {
                    return false;
                }
            } /*
               * This else if is done to ensure it is a Seat and not other type of Position if
               * they were to be added in the future. As flying positions
               */
        }
        for (Position p : pos) {
            if (occupy(p) == false)
                return false;
        }
        return true;
    }

    /**
     * Occupies a certain seat in the performance
     * 
     * @param p Seat to be occupied
     */
    public boolean occupy(Position p) {
        Seat s;
        Sitting area = (Sitting) super.associated;
        if (!(p instanceof Seat)) {
            return false;
        }
        if (isOccupied(p))
            return false;
        s = (Seat) p;
        // allSeats[row][col] = 0;
        for (int i = 0; i < area.getRows(); i++) {
            for (int j = 0; j < area.getCols(); j++) {
                /**
                 * New value to assign is the minimum between the new distance to the seat and
                 * the distance that was established previosly. This results in
                 */
                allSeats[i][j] = Math.min(s.distanceTo(area.getSeat(i + 1, j + 1)), allSeats[i][j]);
            }
        }
        occupiedSeats.add(s);
        return true;
    }

    /**
     * Unoccupies a certain seat in the performnce
     * 
     * @param p Seat to be unoccupied
     * @return True if postion could be unoccupied
     */
    public boolean unoccupy(Position p) {
        Seat s;
        if (!(p instanceof Seat)) {
            return false;
        }
        if (!isOccupied(p))
            return false;
        s = (Seat) p;
        occupiedSeats.remove(s);
        initMatrix();
        for (Seat aux : occupiedSeats) {
            occupy(aux);
        }
        return true;
    }

    /**
     * Uniccupies a list of positions
     * 
     * @param pos List of positions to be unoccupied
     * @return True if seats were unoccupied
     */
    public boolean unoccupy(List<Position> pos) {
        Set<Seat> seats = new HashSet<>();
        for (Position p : pos) {
            if (p instanceof Seat)
                seats.add((Seat) p);
            else
                return false;
        }
        occupiedSeats.removeAll(seats);
        initMatrix();
        for (Seat s : occupiedSeats) {
            occupy(s);
        }
        return true;
    }

    /**
     * @param p Seat to be considered
     * @return True if seat is occupied. False otherwise
     */
    public boolean isOccupied(Position p) {
        Seat s;
        if (!(p instanceof Seat)) {
            return true;
            /*
             * We are returning true to indicate that seat is occupied. This error message
             * cannot be distinguished from the seat actually being occupied
             */
        }
        s = (Seat) p;
        return allSeats[s.getRow() - 1][s.getColumn() - 1] <= 0;

    }

    /**
     * @return Number of seats available in the area associated
     */
    public int getAvailablePositons() {
        return super.associated.getCapacity() - occupiedSeats.size() - super.restric - disabledSeatsCount;
    }

    /**
     * @return Number of seats occupied in the area associated
     */
    public int getOccupiedPossitions() {
        return this.occupiedSeats.size();
    }

    /**
     * This method generates a set of seats, non occupied, based on far from others
     * heuristics
     * 
     * @param n Number of seats requested
     * @return The set of seats. null if any error
     */
    public Set<Seat> getFarSeatsNearSet(int n) {
        Set<Seat> s;
        Sitting sit = (Sitting) super.associated;
        showMatrix();
        if (getAvailablePositons() < n) {
            return null;
        }
        s = new HashSet<>();
        int maxDist = 0, rowMax = 1, colMax = 1;
        for (int i = 0; i < sit.getRows(); i++) {
            for (int j = 0; j < sit.getCols(); j++) {
                if (allSeats[i][j] > maxDist) {
                    maxDist = allSeats[i][j];
                    rowMax = i + 1;
                    colMax = j + 1;
                }
            }
        }

        Seat here = sit.getSeat(rowMax, colMax);
        for (int i = 0; i < n; i++) {
            s.add(here);
            allSeats[here.getRow() - 1][here.getColumn() - 1] *= -1; // Marking seat as dont use
            if ((here = getValidAdjacent(here)) == null) {
                // If there are no adjacent, we find the next furthest away seat
                int localMax = 0;
                for (int j = 0; j < sit.getRows(); j++) {
                    for (int k = 0; k < sit.getCols(); k++) {
                        if (allSeats[j][k] > localMax) {
                            localMax = allSeats[j][k];
                            here = sit.getSeat(j + 1, k + 1);
                        }
                    }
                }
            }

        }

        for (Seat revert : s) {
            allSeats[revert.getRow() - 1][revert.getColumn() - 1] *= -1; // Removing the mark
        }
        return s;

    }

    /**
     * Returns the available seat which is adjacent. This method works after
     * multiple calls because the seat "here" is set to a negative value. Therefore,
     * it is not taken into consideration when selecting a valid seat;
     * 
     * @param here Seat selected
     * @return Seat adjacent to "here", with a valid number
     */
    private Seat getValidAdjacent(Seat here) {
        int r, c, rowMax = 1, colMax = 1, dr, dc, maxVal = 0;
        Sitting sit = (Sitting) super.associated;
        r = here.getRow() - 1;
        c = here.getColumn() - 1;

        dc = c;
        for (dr = r - 1; dr <= r + 1; dr += 2) {
            if (dr < 0 || dr >= sit.getRows()) {
                continue;
            }
            if (allSeats[dr][dc] > maxVal) {
                maxVal = allSeats[dr][dc];
                rowMax = dr + 1;
                colMax = dc + 1;
            }

        }
        dr = r;
        for (dc = c - 1; dc <= c + 1; dc += 2) {
            if (dc < 0 || dc >= sit.getCols()) {
                continue;
            }
            if (allSeats[dr][dc] > maxVal) {
                maxVal = allSeats[dr][dc];
                rowMax = dr + 1;
                colMax = dc + 1;
            }

        }

        if (maxVal == 0)
            return null;
        return sit.getSeat(rowMax, colMax);

    }

    /**
     * @return Prints the matrix of the sitting area in the present
     */
    public String showMatrix() {
        String aux = "";
        Sitting a = (Sitting) super.associated;
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                aux += allSeats[i][j] + " ";
            }
            aux += "\n";
        }
        return aux;
    }

    /**
     * Regenerates the matrix. Used if performance is postponed
     *
     * @return Set of seats that have been removed.
     */
    public Set<Seat> regenMatrix() {
        Set<Seat> rem = new HashSet<>();
        initMatrix();
        for (Seat s : occupiedSeats) {
            if (occupy(s) == false) {
                rem.add(s);
            }
        }
        return rem;
    }

    /**
     * Removes a seat that has been disabled and regenetes the matrix of seats
     * 
     * @param s Seat that has been removed
     */
    public void seatHasBeenDisabled(Seat s) {
        occupiedSeats.remove(s);
        regenMatrix(); // Regardless the seat was occupied, we need to change the matrix
    }

    /**
     * @return Matrix representing the occupancy of the sitting area. 1 indicates
     *         available, 0 indicates occupied or disabled and -1 a no usage
     */
    public int[][] matrixOccupancy() {
        int[][] ret = new int[allSeats.length][allSeats[0].length];

        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret[0].length; j++) {
                if (allSeats[i][j] > 0)
                    ret[i][j] = 1;
                else if (allSeats[i][j] == 0)
                    ret[i][j] = 0;
                else
                    ret[i][j] = -1;
            }
        }
        return ret;
    }

}
