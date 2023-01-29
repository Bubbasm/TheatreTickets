package model. acts.events;

import model. exceptions.*;

/**
 * Music event class
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class MusicEvent extends Event {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Orchestra playing in the music event */
    private String orchestra;
    /** Soloist playing in the music event */
    private String soloist;
    /** Program to be shown to users */
    private String program;

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
     * @param orch    Orchestra playing during music event
     * @param solo    Soloist playing during music event
     * @param prog    Program that event will follow
     * @throws CreationException If any invalid argument
     */
    public MusicEvent(String name, String title, int dur, String desc, String author, String direc, double restric,
            String orch, String solo, String prog) throws CreationException {
        super(name, title, dur, desc, author, direc, restric);
        if (orch == null || orch.equals(""))
            throw new CreationException("No orchestra for music event");
        this.orchestra = orch;
        if (solo == null || solo.equals(""))
            throw new CreationException("No soloist for music event");
        this.soloist = solo;
        if (prog == null || prog.equals(""))
            throw new CreationException("No program for music event");
        this.program = prog;
    }

    /**
     * Getter of orchestra
     * 
     * @return Music event's orchestra
     */
    public String getOrchestra() {
        return orchestra;
    }

    /**
     * Getter of soloist
     * 
     * @return Music event's soloist
     */
    public String getSoloist() {
        return soloist;
    }

    /**
     * Getter for evnet's program
     * 
     * @return Music event's program
     */
    public String getProgram() {
        return program;
    }

    /**
     * @return Full description of the event (description + director + duration + ... )
     */
    @Override
    public String getFullDescription(){
        String str = super.getFullDescription();
        str += "Orchestra: "+this.orchestra+"\n";
        str += "Soloist: "+this.soloist+"\n\n";
        str += "Program: "+this.program+"\n";
        return str;
    }
}
