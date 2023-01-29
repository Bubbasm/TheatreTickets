package model. acts.cycles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model. acts.events.Event;
import model. areas.NonComposite;

/**
 * Class that represent a cycle of events
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Cycle implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Cycle name */
    private String name;
    /** Events that the cycle is made of */
    private Set<Event> events;
    /** Mapping of areas and their discounts */
    private Map<NonComposite, Double> discounts;

    /**
     * Constructor
     * 
     * @param name   name for the cycle
     * @param events set of events that are in the cycle
     */
    public Cycle(String name, Set<Event> events) {
        this.name = name;
        this.events = events;
        this.discounts = new HashMap<>();
    }

    /**
     * Getter for the discount
     * 
     * @param area area to get the discount associated
     * @return the discount
     */
    public double getDiscount(NonComposite area) {
        return this.discounts.get(area);
    }

    /**
     * Setter for the discount
     * 
     * @param area     area to associate the discount
     * @param discount double, between 0 and 1. If the discount is X%, then this
     *                 value should be X/100 (in other words, final_price = price *
     *                 (1-discount))
     */
    public void setDiscount(NonComposite area, double discount) {
        this.discounts.put(area, discount);
    }

    /**
     * Getter for the name
     * 
     * @return the name of the cycle
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the name
     * 
     * @return the name of the cycle
     */
    public Set<Event> getEvents() {
        return this.events;
    }

    /** */
    @Override
    public String toString() {
        String str = "Cycle with name " + this.getName() + " and events: ";
        for (Event e : this.getEvents()) {
            str += "\"" + e.getName() + "\" ";
        }
        return str;
    }
}
