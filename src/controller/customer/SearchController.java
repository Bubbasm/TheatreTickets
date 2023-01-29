package controller.customer;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.awt.*;

import view.*;
import view.customer.search.*;
import model.acts.performances.*;
import model.acts.cycles.*;

import java.util.*;
import java.util.List;

import controller.MainController;
import controller.shared.Controller;
import model.app.TheatreTickets;
import model.acts.events.Event;

/**
 * Generic search controller, handling all events, cycles and performance
 * entries
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class SearchController extends Controller {

    /** Searching panel */
    protected Search panel;
    /** Main controller */
    protected MainController mc;

    /** Map containing all shown events */
    protected Map<String, Event> strEvent;
    /** Map containing all shown cycles */
    protected Map<String, Cycle> strCycle;
    /** Map containing all shown performances */
    protected Map<String, Performance> strPerformance;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public SearchController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Getter for searching
     *
     * @return The action listener
     */
    public ActionListener getSearchHandler() {
        return a -> {
            SearchType se = panel.getSearchType();
            view.customer.search.SearchFilter sf = panel.getSearchFilter();

            panel.emptySearch();
            strEvent = new HashMap<>();
            strCycle = new HashMap<>();
            strPerformance = new HashMap<>();

            int i = 0;
            if (se.equals(SearchType.EVENT)) {
                List<Event> events = instance.searchEvent(panel.getSearch());
                for (Event e : events) {
                    Integer uid = Integer.valueOf(i++);
                    EventEntry ee = getEventEntry(e, uid);
                    panel.addSearchEntry(ee);
                }
            } else if (se.equals(SearchType.CYCLE)) {

                List<Cycle> cyc = instance.searchCycle(panel.getSearch());
                addCycleEntries(cyc);

            } else if (se.equals(SearchType.PERFORMANCE)) {
                // Search with a certain filter
                List<Performance> perf = instance.searchPerformance(panel.getSearch(),
                        model.enums.SearchFilter.valueOf(sf.toString()));

                for (Performance p : perf) {
                    Integer uid = Integer.valueOf(i++);
                    PerformanceEntry pe = getPerformanceEntry(p, true, uid);
                    panel.addSearchEntry(pe);
                }
            }
        };
    }

    /**
     * Getter for the performance entry to be generated. Differs between registered
     * and unregisterd user
     *
     * @param p    Performance to add
     * @param bool If event details must be shown
     * @param uid  Unique id for entry, to keep track of button pressed
     * @return The performance entry
     */
    protected abstract PerformanceEntry getPerformanceEntry(Performance p, boolean bool, Integer uid);

    /**
     * Getter for an event entry more details button action
     *
     * @return The action listener
     */
    private ActionListener getEventEntryHandler() {
        return a -> {
            Event event = strEvent.get(a.getActionCommand());
            Set<Performance> perfs = event.getPerformances();
            EventEntryDetailed eed = new EventEntryDetailed("", event.getTitle(), event.getFullDescription());

            strPerformance = new HashMap<>();
            int i = 0;
            for (Performance p : perfs) {
                if (p.getDate().isBefore(LocalDateTime.now()))
                    continue;
                Integer uid = Integer.valueOf(i++);
                PerformanceEntry pe = getPerformanceEntry(p, false, uid);
                eed.addPerformanceEntry(pe);
            }

            view.updateEventEntryDetailedView(eed);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.EventEntryDetailedName);
        };
    }

    /**
     * Getter for the event entry to be generated.
     *
     * @param e   Event to add
     * @param uid Unique id for entry, to keep track of button pressed
     * @return The event entry
     */
    private EventEntry getEventEntry(Event e, Integer uid) {
        EventEntry ee = new EventEntry(e.getTitle(), e.getAuthor());

        ee.setButtonActionCommand(uid.toString());
        strEvent.put(uid.toString(), e);

        ee.setButtonHandler(getEventEntryHandler());

        return ee;
    }

    /**
     * Creates and adds cycle entries to search panel
     * 
     * @param cyc Collection of cycles to add
     */
    private void addCycleEntries(Collection<Cycle> cyc) {
        int i = 0;
        for (Cycle c : cyc) {
            CycleEntry ce = new CycleEntry(c.getName());

            Integer uid = Integer.valueOf(i++);
            ce.setButtonActionCommand(uid.toString());
            strCycle.put(uid.toString(), c);

            ce.setButtonHandler(getCycleEntryHandler());
            panel.addSearchEntry(ce);
        }

    }

    /**
     * Getter for an cycle entry more details button action
     *
     * @return The action listener
     */
    private ActionListener getCycleEntryHandler() {
        return a -> {
            Cycle cyc = strCycle.get(a.getActionCommand());
            Set<Event> eves = cyc.getEvents();
            CycleEntryDetailed ced = new CycleEntryDetailed(cyc.getName());

            strEvent = new HashMap<>();

            int i = 0;
            for (Event e : eves) {
                Integer uid = Integer.valueOf(i++);
                EventEntry ee = getEventEntry(e, uid);
                ced.addEventEntry(ee);
            }
            view.updateCycleEntryDetailedView(ced);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.CycleEntryDetailedName);
        };
    }

}
