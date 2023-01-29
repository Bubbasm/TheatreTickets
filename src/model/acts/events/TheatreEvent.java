package model. acts.events;

import java.util.*;

import model. exceptions.*;

/**
 * Theatre event class
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TheatreEvent extends Event {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** List of actors performing in the event */
    private List<String> actors;

    /**
     * Music Event constructor
     *
     * @param name    Event's name
     * @param title   Event's title
     * @param dur     Event's duration
     * @param desc    Event's description
     * @param author  Event's author
     * @param direc   Event's director
     * @param restric Restriction to be applied. Between 0 and 1
     * @param actors  Actors playing this event
     * @throws CreationException If any invalid parameter
     */
    public TheatreEvent(String name, String title, int dur, String desc, String author, String direc, double restric,
            List<String> actors) throws CreationException {
        super(name, title, dur, desc, author, direc, restric);
        if (actors == null || actors.isEmpty())
            throw new CreationException("No actors performing in the theatre event");
        this.actors = actors;
    }

    /**
     * @return Actors involved
     */
    public String getActors() {
        return actors.toString();
    }

    /**
     * @return Full description of the event (description + director + duration + ... )
     */
    @Override
    public String getFullDescription(){
        String str = super.getFullDescription();
        str += "Actors:\n";
        for(String s : this.actors)
            str += "  - "+s+"\n";
        return str;
    }
}
