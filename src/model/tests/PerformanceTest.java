package model. tests;

import static org.junit.Assert.*;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.*;

import model. users.*;
import model. exceptions.*;
import model. areas.*;
import model. acts.performances.*;
import model. app.TheatreTickets;
import model. acts.events.*;

/**
 * Tester for {@link Performance}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceTest {

    private Performance p;
    private Sitting si;
    private Standing st;
    private Composite c;

    /**
     * Sets up variables
     * 
     * @throws CreationException If any invalid parameters
     */
    @Before
    public void setUp() throws CreationException {
        TheatreTickets th = TheatreTickets.getInstance();
        c = new Composite("Composite1");
        si = new Sitting("NonComposite1", 2, 2);
        st = new Standing("NonComposite", 33);
        c.addArea(st);
        th.addArea(c);
        th.addArea(si, 69.0);
        th.addArea(st, 82.0);

        Event e = new TheatreEvent("Name", "Title", 69, "Description", "Author", "Director", 0,
                Arrays.asList("Actor1", "Actor2"));
        e.addAreaPrice(si, 232);
        e.addAreaPrice(st, 212);
        th.addEvent(e);
        p = new Performance(e, LocalDateTime.of(2021, 8, 1, 16, 0, 0));
        th.addPerformance(p);
    }

    /** Checks if the waiting list fulfills operations */
    @Test
    public void checkWaitingList() {
        Customer c1 = new Customer("Name1", "Password");
        Customer c2 = new Customer("Name2", "Password");
        p.enterWaitingList(c1);
        assertTrue(p.occupy(si, List.of(si.getSeat(1, 1), si.getSeat(1, 2), si.getSeat(2, 1), si.getSeat(2, 2))));
        assertTrue(p.occupy(st, Collections.nCopies(33, st.getAPosition())));

        p.enterWaitingList(c2);
        p.unoccupy(st, Collections.nCopies(31, st.getAPosition()));
        assertEquals(1, c2.getNumberOfNotifications());
        assertEquals(0, c1.getNumberOfNotifications());
        // System.out.println(c2.showNotifications());
    }

    /** Avalability of multiple areas */
    @Test
    public void availabilityInAreas() {
        assertTrue(p.availableSeat(si, si.getSeat(1, 1)));
        assertTrue(p.occupy(si, List.of(si.getSeat(1, 1), si.getSeat(2, 2))));
        assertFalse(p.availableSeat(si, si.getSeat(1, 1)));
        assertFalse(p.occupy(si, List.of(si.getSeat(1, 1))));

        assertTrue(p.availableCapacity(st, 33));
        assertTrue(p.occupy(st, Collections.nCopies(31, st.getAPosition())));
        assertTrue(p.availableCapacity(st, 2));
        assertFalse(p.availableCapacity(st, 3));
        assertFalse(p.occupy(st, Collections.nCopies(3, st.getAPosition())));
    }

    /** Checks occupy and unoccupy seats */
    @Test
    public void occupyAndUnoccupy() {

        assertTrue(p.occupy(si, List.of(si.getSeat(1, 1), si.getSeat(2, 2))));
        assertFalse(p.occupy(si, List.of(si.getSeat(1, 1))));
        // System.out.println(p.areaOccupancy(si));
        p.unoccupy(si, List.of(si.getSeat(1, 1)));
        // System.out.println(p.areaOccupancy(si));
        assertTrue(p.occupy(si, List.of(si.getSeat(1, 1))));
        assertFalse(p.occupy(si, List.of(si.getSeat(2, 2))));

        assertTrue(p.availableCapacity(st, 33));
        assertTrue(p.occupy(st, Collections.nCopies(31, st.getAPosition())));
        assertFalse(p.availableCapacity(st, 3));
        p.unoccupy(st, Collections.nCopies(31, st.getAPosition()));
        assertTrue(p.availableCapacity(st, 3));

        p.cancelPerformance();

        assertFalse(p.occupy(st, Collections.nCopies(31, st.getAPosition())));
        assertFalse(p.occupy(si, List.of(si.getSeat(1, 1), si.getSeat(2, 2))));

    }

    /** Resets TheatreTickets instance */
    @After
    public void reset() {
        TheatreTickets.restore();
    }
}
