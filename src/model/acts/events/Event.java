package model. acts.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import model. exceptions.*;
import model. areas.*;
import model. acts.performances.*;

/**
 * This class describes a general event
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class Event implements Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Event name */
    private String name;
    /** Event title, as is shown to customers */
    private String title;
    /** Total duration of the whole event */
    private int duration;
    /** Descripton of the event */
    private String description;
    /** Author of event */
    private String author;
    /** Director of event */
    private String director;
    /**
     * Restriction capacity applied to the maximum capacity. Real capacity is
     * (1-restriction)*capacity
     */
    private double restriction;
    /** List of all performances associated with this event */
    private Set<Performance> perfAssoc = new HashSet<>();
    /** Map of all prices depending on the area */
    private Map<NonComposite, Double> areaPrice = new HashMap<>();

    /**
     * Constructor of an event
     *
     * @param name    Event's name
     * @param title   Event's title
     * @param dur     Event's duration
     * @param desc    Event's description
     * @param author  Event's author
     * @param direc   Event's director
     * @param restric Restriction to be applied. Between 0 and 1. Real capacity is
     *                (1-restriction)*capacity
     * @throws CreationException If any invalid parameter
     */
    public Event(String name, String title, int dur, String desc, String author, String direc, double restric)
            throws CreationException {
        if (name == null || name.equals(""))
            throw new CreationException("No name for event");
        this.name = name;
        if (title == null || title.equals(""))
            throw new CreationException("No title for event");
        this.title = title;
        if (dur <= 0)
            throw new CreationException("Negative duration for event");
        this.duration = dur;
        if (desc == null || desc.equals(""))
            throw new CreationException("No description for event");
        this.description = desc;
        if (author == null || author.equals(""))
            throw new CreationException("No author for event");
        this.author = author;
        if (direc == null || direc.equals(""))
            throw new CreationException("No director for event");
        this.director = direc;
        if (restric >= 1 || restric < 0)
            throw new CreationException("Invalid restiction capaticy");
        this.restriction = restric;
    }

    /**
     * Adds a performance to an event
     * 
     * @param p Performance to be added
     */
    public void addPerformance(Performance p) {
        perfAssoc.add(p);
    }

    /**
     * @return Restriction of capacity between 0 and 1
     */
    public double getRestriction() {
        return this.restriction;
    }

    /* There is no setter for restriction, as it can't be set again */

    /**
     * Calculates maximum capacity taking into account the possible restriction
     *
     * @param a Area to be considered
     * @return Real capacity of an area
     */
    public int getRestrictedCapacity(NonComposite a) {
        return (int) ((1 - this.restriction + 0.) * a.getCapacity());
    }

    /**
     * Computes the total reveue per area of an event.
     *
     * @param area Area to consider
     * @return Total revenue in the present
     */
    public double getRevenue(NonComposite area) {
        double totalRev = 0.0;

        for (Performance p : perfAssoc) {
            totalRev += p.getRevenue(area);
        }
        return totalRev;
    }

    /**
     * Assigns the price of an area
     *
     * @param a     Area to be priced
     * @param price Price to add
     */
    public void addAreaPrice(NonComposite a, double price) {
        areaPrice.put(a, price);
    }

    /**
     * @param area Area to be considered
     * @return Price of the specified area. -1 if no area matches
     */
    public double getPrice(NonComposite area) {
        return areaPrice.get(area);
    }

    /**
     * @return Name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * @return Title of the event
     */

    public String getTitle() {
        return title;
    }

    /**
     * @return Duration of the event
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return Description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Full description of the event (description + director + duration + ... )
     */
    public String getFullDescription(){
        String str = "Duration: "+duration+" min.\n\n";
        str += getDescription()+"\n\n";
        str += "Author: "+this.author+"\n";
        str += "Director: "+this.director+"\n";
        return str;
    }

    /**
     * @return Author of the event
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return Director of the event
     */
    public String getDirector() {
        return director;
    }

    /**
     * @return Set of performances that this event has associated
     */
    public Set<Performance> getPerformances() {
        return perfAssoc;
    }

    /**
     * Gets the performance corresponding that date
     *
     * @param d Date
     * @return the performance
     */
    public Performance getPerformanceByDate(LocalDateTime d){
        for(Performance p : this.getPerformances()){
            if(p.getDate().truncatedTo(ChronoUnit.SECONDS).isEqual(d))
                return p;
        }
        return null;
    }

    /**
     * Checks if two events are equal
     * @param o the object to compare this to
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Event)){
            return false;
        }
        if(this == o){
            return true;
        }
        Event e = (Event)o;
        return e.getTitle().equals(this.getTitle()) || e.getName().equals(this.getName());
    }

    /**
     * @return String with the information of an event
     */
    @Override
    public String toString() {
        return "Event: \"" + this.getTitle() + "\", of " + this.getDuration() + " minutes and with "
                + (100 * this.getRestriction()) + "% capacity restriction";
    }
}
