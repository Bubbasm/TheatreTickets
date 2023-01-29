package model. areas;

import model. areas.positions.*;

/**
 * Class for the standing type areas. Child of the class NonComposite.
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Standing extends NonComposite {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** Capacity of the area */
    private int capacity;
    /**
     * Instance of a position of this area. Simulates the seats but for Standing
     * areas because it doesnt make sense to have an array of Positions, so we chose
     * this implementation
     */
    private PositionIdentifier positionInstance;

    /**
     * Constructor
     * 
     * @param name name of the area
     * @param c    capacity
     */
    public Standing(String name, int c) {
        super(name);
        this.capacity = c;
        this.positionInstance = new PositionIdentifier(this);
    }

    /**
     * Method that returns the capacity of the area
     * 
     * @return an integer with the capacity
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Setter for the capacity
     * 
     * @param c capacity
     */
    public void setCapacity(int c) {
        this.capacity = c;
    }

    /**
     * Returns a position of this area
     * 
     * @return the PositionIdentifier Object
     */
    public PositionIdentifier getAPosition() {
        return this.positionInstance;
    }

    /**
     * Method to print the area information
     * 
     * @return the String with the info
     */
    @Override
    public String toString() {
        return super.toString() + ", type: Standing";
    }
}
