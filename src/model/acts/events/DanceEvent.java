package model. acts.events;

import java.util.*;

import model. exceptions.*;

/**
 * Dance event class
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class DanceEvent extends Event {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /** Orchestra playing in the music event */
    private String orchestra;
    /** Dancers performing in the music event */
    private List<String> dancers;
    /** Conductor of the dance event */
    private String conductor;

    /**
     * Dance Event constructor
     *
     * @param name    Event's name
     * @param title   Event's title
     * @param dur     Event's duration
     * @param desc    Event's description
     * @param author  Event's author
     * @param direc   Event's director
     * @param restric Restriction to be applied. Between 0 and 1
     * @param orch    Orchestra playing during music event
     * @param danc    Dancers performing dance event
     * @param cond    Conductor of the dance event
     * @throws CreationException If any invalid argument
     */
    public DanceEvent(String name, String title, int dur, String desc, String author, String direc, double restric,
            String orch, List<String> danc, String cond) throws CreationException {
        super(name, title, dur, desc, author, direc, restric);
        if (orch == null || orch.equals(""))
            throw new CreationException("No orchestra for music event");
        this.orchestra = orch;
        if (danc == null || danc.isEmpty())
            throw new CreationException("No dancers for music event");
        this.dancers = danc;
        if (cond == null || cond.equals(""))
            throw new CreationException("No conductor for music event");
        this.conductor = cond;
    }

    /**
     * Getter of orchestra
     * 
     * @return Dance event's orchestra
     */
    public String getOrchestra() {
        return orchestra;
    }

    /**
     * Getter of dancers
     * 
     * @return Dance event's dancers
     */
    public String getDancers() {
        return dancers.toString();
    }

    /**
     * Getter for conductor
     * 
     * @return Dance event's conductor
     */
    public String getConductor() {
        return conductor;
    }

    /**
     * @return Full description of the event (description + director + duration + ... )
     */
    @Override
    public String getFullDescription(){
        String str = super.getFullDescription();
        str += "Orchestra: "+this.orchestra+"\n";
        str += "Conductor: "+this.conductor+"\n\n";
        str += "Dancers:\n";
        for(String s : this.dancers)
            str += "  - "+s+"\n";
        return str;
    }
}
