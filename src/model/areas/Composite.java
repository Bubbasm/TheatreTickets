package model.areas;

import java.util.Set;
import java.util.HashSet;

/**
 * Class for the composite areas. Child of the class Area.
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Composite extends Area {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /**
     * private attributes
     */
    private Set<Area> areas;

    /**
     * Constructor
     * 
     * @param name name of the area
     */
    public Composite(String name) {
        super(name);
        this.areas = new HashSet<>();
    }

    /**
     * Method that returns the capacity of the area adding up all the capacities of
     * its areas
     * 
     * @return an integer with the capacity
     */
    @Override
    public int getCapacity() {
        int total = 0;
        for (Area a : this.areas) {
            total += a.getCapacity();
        }
        return total;
    }

    /**
     * Getter for the areas
     * 
     * @return the areas inside of the composite area
     */
    public Set<Area> getAreas() {
        return this.areas;
    }

    /**
     * Method to add a new area inside of this one
     * 
     * @param a area to add
     * @return true if the area has been added succesfully
     */
    public boolean addArea(Area a) {
        boolean check = false;
        /* if the area is composite we have to check that it doesnt contain this area */
        if (a instanceof Composite) {
            check = ((Composite) a).contains(this);
        }
        if (this.equals(a) || check) {
            return false;
        }
        return this.getAreas().add(a);
    }

    /**
     * Method to add multiple areas inside of this one
     * 
     * @param areas areas to add
     * @return true if EVERY area was added succesfully
     */
    public boolean addAreas(Area... areas) {
        boolean flag = true;
        for (Area a : areas) {
            flag = (flag && this.addArea(a));
        }
        return flag;
    }

    /**
     * Method to remove an area from this one. If the area is contained in any
     * subarea it is not removed
     * 
     * @param a Area to remove
     * @return true if the area was contained
     */
    public boolean removeArea(Area a) {
        return this.getAreas().remove(a);
    }

    /**
     * Method to remove multiple areas from this one
     * 
     * @param areas Areas to remove
     * @return true if EVERY area was removed succesfully
     */
    public boolean removeAreas(Area... areas) {
        boolean flag = true;
        for (Area a : areas) {
            flag = (flag && this.removeArea(a));
        }
        return flag;
    }

    /**
     * Checks if an area is inside this one or inside any subarea
     * 
     * @param area area to check if is contained
     * @return true if this contains the area, false otherwise
     */
    public boolean contains(Area area) {
        if (this.strictContains(area))
            return true;
        for (Area a : this.getAreas()) {
            if (a instanceof Composite) {
                if (((Composite) a).contains(area))
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if an area is strictly inside this one (not any subarea)
     * 
     * @param area area to check if is contained
     * @return true if this contains the area, false otherwise
     */
    public boolean strictContains(Area area) {
        return this.getAreas().contains(area);
    }

    /**
     * Getter for the NonComposite areas inside of this one
     * 
     * @return the areas that are NonComposite and are inside of this one (or inside
     *         of any subarea)
     */
    public Set<NonComposite> getNonCompositeWithin() {
        Set<NonComposite> areas = new HashSet<>();
        for (Area a : this.getAreas()) {
            if (a instanceof Composite) {
                areas.addAll(((Composite) a).getNonCompositeWithin());
            } else {
                areas.add((NonComposite) a);
            }
        }
        return areas;
    }

    /**
     * Method to print the area information
     * 
     * @return the String with the info
     */
    @Override
    public String toString() {
        return super.toString() + ", type: Composite, areas inside: " + this.getAreas().toString();
    }

    /**
     * Gets the area with a certain name
     *
     * @param name name to get
     * @return Area with the same name. null if not found
     */
    public Area getAreaByName(String name) {
        Area result = null;
        for (Area a : this.getAreas()) {
            if (a.getName().equals(name))
                return a;
            if (a instanceof Composite) {
                result = ((Composite) a).getAreaByName(name);
            }
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
