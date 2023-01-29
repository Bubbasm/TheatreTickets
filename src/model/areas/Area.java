package model.areas;

import java.io.Serializable;

/**
 * Abstract class for all the area types
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public abstract class Area implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** name of the area */
    private String name;

    /**
     * Constructor
     * 
     * @param name name of the area
     */
    public Area(String name) {
        this.name = name;
    }

    /**
     * Abstract Method that returns the capacity of the area
     * 
     * @return an integer with the capacity
     */
    public abstract int getCapacity();

    /**
     * Getter for the name of the area
     * 
     * @return name of the area
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if two areas are equal
     * 
     * @param o the object to compare this to
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Area)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Area a = (Area) o;
        return a.getName().equals(this.getName());
    }

    /**
     * Method to print the area information
     * 
     * @return the String with the info
     */
    @Override
    public String toString() {
        return "Area name: " + this.getName() + ", capacity: " + this.getCapacity();
    }
}
