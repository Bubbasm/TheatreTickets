package model.app;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.time.LocalDateTime;

import model.areas.*;
import model.acts.cycles.Cycle;
import model.acts.events.*;
import model.acts.performances.*;
import model.users.*;
import model.enums.PerformanceStatus;
import model.enums.SearchFilter;
import model.exceptions.*;

/**
 * This class is the app class that holds constants and contains all the events,
 * performances and users. Follows a singleton pattern
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TheatreTickets implements Serializable {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** unique instance of this class */
    private static TheatreTickets INSTANCE;
    /** UID for operation */
    private int lastOperationID;
    /** UID for ticket */
    private int lastTicketID;
    /** variable for the app */
    private int maxTicketsPerPurchase;
    /** variable for the app */
    private int maxTicketsPerReservation;
    /** variable for the app */
    private int hoursForReservation;
    /** List of dance events */
    private List<DanceEvent> danceEvents;
    /** List of theatre events */
    private List<TheatreEvent> theatreEvents;
    /** List of music events */
    private List<MusicEvent> musicEvents;
    /** List of performances */
    private List<Performance> performances;
    /** Mapping of a username to a customer */
    private Map<String, Customer> customers;
    /** Current logged in User. Do not want serialization */
    private static User currentUser = null;
    /** Unique manager for the app */
    private Manager manager;
    /** List of cycles */
    private List<Cycle> cycles;
    /** Mappiing for areas and their annual pass price */
    private Map<Area, Double> passPrices;
    /** Main area. This contains the structure of the theatre */
    private Composite mainArea;

    /**
     * Private Constructor
     */
    private TheatreTickets() {
        this.danceEvents = new LinkedList<>();
        this.musicEvents = new LinkedList<>();
        this.theatreEvents = new LinkedList<>();
        this.performances = new LinkedList<>();
        this.customers = new HashMap<>();
        this.cycles = new LinkedList<>();
        this.passPrices = new HashMap<>();
        this.mainArea = new Composite("Theatre");
        // this.managers = new HashSet<>();
        // this.managers.addManager(new Manager("admin", "admin"));
        this.manager = new Manager("admin", "admin");
    }

    /**
     * Getter for the INSTANCE of this class (there is only one as it follows a
     * singleton pattern)
     * 
     * @return the instance
     */
    public static TheatreTickets getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TheatreTickets();
            INSTANCE.hoursForReservation = 8;
            INSTANCE.maxTicketsPerPurchase = 10;
            INSTANCE.maxTicketsPerReservation = 5;
        }
        return INSTANCE;
    }

    /**
     * Restores the system to be empty (new INSTANCE generated) Mainly used in the
     * testers
     */
    public static void restore() {
        INSTANCE = new TheatreTickets();
    }

    /**
     * Adds an event to the Set of events
     * 
     * @param e event to add
     * @return true if the event wasn't already contained
     */
    public boolean addEvent(Event e) {
        if (e instanceof DanceEvent) {
            if (this.danceEvents.contains(e))
                return false;
            return this.danceEvents.add((DanceEvent) e);
        } else if (e instanceof MusicEvent) {
            if (this.musicEvents.contains(e))
                return false;
            return this.musicEvents.add((MusicEvent) e);
        } else if (e instanceof TheatreEvent) {
            if (this.theatreEvents.contains(e))
                return false;
            return this.theatreEvents.add((TheatreEvent) e);
        }
        return false;
    }

    /**
     * Add a performance to the set of performances
     * 
     * @param p performance to add
     * @return true if the performance wasn't already contained
     */
    public boolean addPerformance(Performance p) {
        if (!this.getEvents().contains(p.getEvent())) {
            return false;
        }
        if (performances.contains(p))
            return false;
        return this.performances.add(p);
    }

    /**
     * Add a customer to the app
     * 
     * @param u user to add
     * @return true if the user wasn't already contained
     */
    public boolean addCustomer(Customer u) {
        return this.customers.putIfAbsent(u.getUsername(), u) == null;
    }

    /*
     * Add a manager to the app. This method is comented out as we consider easier
     * to only have one manager
     * 
     * @param m manager to add
     * 
     * @return true if the user wasn't already contained
     */
    /*
     * public boolean addManager(Manager m) { return this.managers.add(m); }
     */

    /*
     * Getter for the managers. This method is comented out as we consider easier to
     * only have one manager
     * 
     * @return a set with the managers
     */
    /*
     * public Set<Manager> getManagers() { return this.managers; }
     */

    /**
     * method for registering new customers
     * 
     * @param username username handle of the user, uniquely identifies each user
     * @param password authentication that allows the user access to their account
     *                 and the system as a registered user
     * @return the customer if registration is done successfully
     * @throws InvalidUsernameException Cannot register that user
     */
    public Customer register(String username, String password) throws InvalidUsernameException {
        Customer c = new Customer(username, password);
        if (username.equals(this.getManager().getUsername()) || !getInstance().addCustomer(c)) {
            throw new InvalidUsernameException("Username taken: " + username);
        } else {
            return c;
        }
    }

    /**
     * method for the log in
     * 
     * @param username username handle of the user, uniquely identifies each user
     * @param password authentication that allows the user access to their account
     *                 and the system as a registered user
     * @return The logged in user
     * @throws InvalidLoginException Invalid username or password
     */
    public RegisteredUser login(String username, String password) throws InvalidLoginException {
        if (username.equals(this.getManager().getUsername()) && password.equals(this.getManager().getPassword())) {
            currentUser = (Manager) this.getManager();
            return (Manager) currentUser;
        }
        if (this.getCustomers().get(username) != null) {
            if (this.getCustomers().get(username).getPassword().equals(password)) {
                currentUser = (Customer) this.getCustomers().get(username);
                return (Customer) currentUser;
            }
        }
        // else, it isnt an existing user nor the manager
        throw new InvalidLoginException("Invalid user or password");
    }

    /**
     * Sets current customer to null, unlogging the current user
     */
    public void logout() {
        currentUser = new UnregisteredUser();
    }

    /**
     * Add a cycle to the set of cycle
     * 
     * @param c cycle to add
     * @return true if the cycle wasn't already contained
     */
    public boolean addCycle(Cycle c) {
        if (!this.getEvents().containsAll(c.getEvents())) {
            return false;
        }
        if (cycles.contains(c))
            return false;
        return this.cycles.add(c);
    }

    /**
     * Gets the area with a certain name
     *
     * @param name name to get
     * @return Area with the same name. null if not found
     */
    public Area searchArea(String name) {
        if (name.equals("Theatre"))
            return this.getMainArea();
        return this.getMainArea().getAreaByName(name);
    }

    /**
     * Gets the NonComposite with a certain name
     *
     * @param name name to get
     * @return NonComposite with the same name. null if not found
     */
    public NonComposite searchNonCompositeArea(String name) {
        Area aux = this.getMainArea().getAreaByName(name);
        if (aux instanceof NonComposite) {
            return (NonComposite) aux;
        }
        return null;
    }

    /**
     * Add a NonComposite area to the set of areas and also add its price for the
     * annual pass
     * 
     * @param a     area to add
     * @param price price of the annual pass for this area
     * @return true if the area wasn't already contained and there isnt any
     *         performance scheduled
     */
    public boolean addArea(NonComposite a, Double price) {
        Double d = this.passPrices.putIfAbsent(a, price);
        if (d != null)
            return false;
        if (this.getMainArea().contains(a))
            return true;
        return this.commonAddArea(a);
    }

    /**
     * Add a Composite area to the set of areas
     * 
     * @param a area to add
     * @return true if the area wasn't already contained and there isnt any
     *         performance scheduled
     */
    public boolean addArea(Composite a) {
        return this.commonAddArea(a);
    }

    /**
     * Add an area to the set of areas
     * 
     * @param a area to add
     * @return true if the area wasn't already contained and there isnt any
     *         performance scheduled
     */
    private boolean commonAddArea(Area a) {
        // cannot modify the theatre with performances already scheduled
        if (!this.performances.isEmpty()) {
            return false;
        }
        return this.mainArea.addArea(a);
    }

    /**
     * Remove an area to the set of areas
     * 
     * @param a area to add
     * @return true if the area wasn't already contained
     */
    public boolean removeArea(Area a) {
        // cannot modify the theatre with performances already scheduled
        if (!this.performances.isEmpty() || !mainArea.contains(a)) {
            return false;
        }

        this.passPrices.remove(a);

        if (this.getMainArea().strictContains(a)) {
            return this.mainArea.removeArea(a);
        } else {
            for (Area x : this.getMainArea().getAreas()) {
                if (x instanceof Composite) {
                    if (((Composite) x).contains(a)) {
                        ((Composite) x).removeArea(a);
                        return true;
                    }

                }
            }
            return false;
        }
    }

    /**
     * Getter for last ticket id
     *
     * @return the id
     */
    public int getLastTicketID() {
        return lastTicketID;
    }

    /**
     * Increments the ticket id by one
     */
    public void incrementTicketID() {
        lastTicketID++;
    }

    /**
     * Getter for last operation id
     *
     * @return the id
     */
    public int getLastOperationID() {
        return lastOperationID;
    }

    /**
     * Increments the operation id by one
     */
    public void incrementOperationID() {
        lastOperationID++;
    }

    /**
     * Getter for the name of the theatre. Neccesary for ticket generation
     * 
     * @return the theatre's name
     */
    public String getTheatreName() {
        return "MyTheatre";
    }

    /**
     * Getter for the main area
     * 
     * @return the main area
     */
    public Composite getMainArea() {
        return this.mainArea;
    }

    /**
     * Return the price of the annual pass for an area
     * 
     * @param a the area to check the price for
     * @return the price of the annual pass for that area
     */
    public double getPassPrice(NonComposite a) {
        return this.passPrices.get(a);
    }

    /**
     * Getter for the events
     * 
     * @return a set with the events
     */
    public Set<Event> getEvents() {
        Set<Event> events = new HashSet<>();
        events.addAll(this.danceEvents);
        events.addAll(this.musicEvents);
        events.addAll(this.theatreEvents);
        return events;
    }

    /**
     * Getter for the dance events
     * 
     * @return a set with the events
     */
    public List<DanceEvent> getDanceEvents() {
        return this.danceEvents;
    }

    /**
     * Getter for the theatre events
     * 
     * @return a set with the events
     */
    public List<TheatreEvent> getTheatreEvents() {
        return this.theatreEvents;
    }

    /**
     * Getter for the music events
     * 
     * @return a set with the events
     */
    public List<MusicEvent> getMusicEvents() {
        return this.musicEvents;
    }

    /**
     * Getter for the performances
     * 
     * @return a set with the performances
     */
    public List<Performance> getPerformances() {
        return this.performances;
    }

    /**
     * Getter for the customers
     * 
     * @return a set with the customers
     */
    public Map<String, Customer> getCustomers() {
        return this.customers;
    }

    /**
     * Getter for the manager
     * 
     * @return the manager
     */
    public Manager getManager() {
        return this.manager;
    }

    /**
     * Getter for the customer
     * 
     * @return the customer
     */
    public static User getCurrentCustomer() {
        return currentUser;
    }

    /**
     * Getter for the cycles
     * 
     * @return a set with the cycles
     */
    public List<Cycle> getCycles() {
        return this.cycles;
    }

    /**
     * Getter for the maximum number of tickets that can be bought at once
     * 
     * @return the maximum number
     */
    public int getMaxTicketsPerPurchase() {
        return this.maxTicketsPerPurchase;
    }

    /**
     * Setter for the maximum number of tickets that can be bought at once
     * 
     * @param n maximum number
     */
    public void setMaxTicketsPerPurchase(int n) {
        this.maxTicketsPerPurchase = n;
    }

    /**
     * Getter for the maximum number of tickets that can be reserved at once
     * 
     * @return the maximum number
     */
    public int getMaxTicketsPerReservation() {
        return this.maxTicketsPerReservation;
    }

    /**
     * Setter for the maximum number of tickets that can be reserved at once
     * 
     * @param n maximum number
     */
    public void setMaxTicketsPerReservation(int n) {
        this.maxTicketsPerReservation = n;
    }

    /**
     * Getter for the number of hours for reservations to be made and paid within
     * 
     * @return the number of hours
     */
    public int getHoursForReservation() {
        return this.hoursForReservation;
    }

    /**
     * Setter for the number of hours for reservations to be made and paid within
     * 
     * @param n number of hours
     */
    public void setHoursForReservation(int n) {
        this.hoursForReservation = n;
    }

    /**
     * Returns the event that matches the specified string on their title
     * 
     * @param name string to search
     * @return the event
     */
    public Event searchEventByName(String name) {
        for (Event e : this.getEvents()) {
            if (e.getTitle().equals(name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns all the events that contain the specified string on their name/title
     * 
     * @param name string to search
     * @return the events
     */
    public List<Event> searchEvent(String name) {
        List<Event> events = new ArrayList<>();
        for (Event e : this.getEvents()) {
            if (e.getName().toLowerCase().contains(name.toLowerCase())
                    || e.getTitle().toLowerCase().contains(name.toLowerCase())) {
                events.add(e);
            }
        }
        return events;
    }

    /**
     * Returns all the events that contain the specified string on their name/title
     * and of the given type specified on the filter
     * 
     * @param name string to search
     * @return the events
     */
    private List<Event> searchEvent(String name, SearchFilter filter) {
        List<Event> resultEvents = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        if (filter == SearchFilter.DANCE) {
            events.addAll(this.getDanceEvents());
        } else if (filter == SearchFilter.THEATRE) {
            events.addAll(this.getTheatreEvents());
        } else if (filter == SearchFilter.MUSIC) {
            events.addAll(this.getMusicEvents());
        } else { // SearchFilter.FUTURE
            events.addAll(this.getEvents());
        }

        for (Event e : events) {
            if (e.getName().toLowerCase().contains(name.toLowerCase())
                    || e.getTitle().toLowerCase().contains(name.toLowerCase())) {
                resultEvents.add(e);
            }
        }
        return resultEvents;
    }

    /**
     * Returns all the performances that contain the specified string on their
     * name/title
     * 
     * @param name string to search
     * @return the performances
     */
    public List<Performance> searchPerformance(String name) {
        List<Performance> performances = new ArrayList<>();
        for (Event e : this.searchEvent(name)) {
            performances.addAll(e.getPerformances());
        }
        return performances;
    }

    /**
     * Returns all the performances that contain the specified string on their name
     * and of the given type specified in the filter
     * 
     * @param name   string to search on the names
     * @param filter filters the performances by type
     * @return the performances
     */
    public List<Performance> searchPerformance(String name, SearchFilter filter) {
        List<Performance> performances = new ArrayList<>();
        for (Event e : this.searchEvent(name, filter)) {
            for (Performance p : e.getPerformances()) {
                // Filter out cancelled performances
                if (p.getStatus() != PerformanceStatus.CANCELLED) {
                    // Filter out Expired performances only if the filter is FUTURE
                    if ((filter == SearchFilter.FUTURE && p.getStatus() != PerformanceStatus.EXPIRED)
                            || filter != SearchFilter.FUTURE) {
                        performances.add(p);
                    }
                }
            }
        }
        return performances;
    }

    /**
     * Returns the cycle that mathces specified name
     * 
     * @param name string to search
     * @return the cycle
     */
    public Cycle searchCycleByName(String name) {
        for (Cycle c : this.getCycles()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns all the cycles that contain the specified string on their name
     * 
     * @param name string to search
     * @return the cycles
     */
    public List<Cycle> searchCycle(String name) {
        List<Cycle> cycles = new ArrayList<>();
        for (Cycle c : this.getCycles()) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                cycles.add(c);
            }
        }
        return cycles;
    }

    /**
     * Serialize the app to the file indicated in path
     * 
     * @param path path in which you want to save the current instance
     */
    public static void saveTo(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getInstance());
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.err.println("Data was not saved in " + path + ".");
        }
    }

    /**
     * Read and load a previous instance of the app from the file indicated in path
     * 
     * @param path path from which you want to load the app
     * @throws IOException IO error
     * @throws ClassNotFoundException error reading and assiging object
     */
    public static void loadFrom(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        TheatreTickets.INSTANCE = (TheatreTickets) ois.readObject();
        ois.close();
        fis.close();
    }

    /**
     * Disable a seat and update the occupancy matrix in the performances affected
     * 
     * @param area area in which the seat is located
     * @param row  row of the seat
     * @param col  col of the seat
     * @param from date from
     * @param to   date to
     * @return true if it has been disabled correctly
     */
    public boolean disableSeat(Sitting area, int row, int col, LocalDateTime from, LocalDateTime to) {
        // find the area
        for (NonComposite a : this.getMainArea().getNonCompositeWithin()) {
            if (a.equals(area)) {
                // disable the seat
                if (((Sitting) a).disable(row, col, from, to))
                    break;
                else
                    return false;
            }
        }

        // look for the performances afected and update the occupancy matrix
        for (Performance p : this.getPerformances()) {
            LocalDateTime d = p.getDate();
            if (d.isAfter(area.getSeat(row, col).getDates().getFrom())
                    && d.isBefore(area.getSeat(row, col).getDates().getTo())) {
                p.seatHasBeenDisabled(area, area.getSeat(row, col));
            }
        }

        return true;
    }

    /**
     * Getter to know if theatre could be edited
     * 
     * @return True is can be edited
     */
    public boolean isEditable() {
        boolean ret = performances.isEmpty();
        ret &= musicEvents.isEmpty();
        ret &= danceEvents.isEmpty();
        ret &= theatreEvents.isEmpty();
        ret &= cycles.isEmpty();
        return ret;
    }
}
