package model. tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import model. areas.*;
import model. areas.positions.*;
import model. exceptions.CreationException;
import model.acts.events.Event;
import model.acts.events.TheatreEvent;
import model.acts.performances.Performance;
import model. acts.performances.organization.*;

/**
 * Tester for {@link PerformanceSitting}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceSittingTest {

    /** Sitting area */
    private Sitting area;
    /** Object to be tested */
    private PerformanceSitting occupancy;
    /** List of seats */
    private List<Seat> lp;

    /**
     * Initializing attributes
     * 
     * @throws CreationException If any invalid argument
     */
    @Before
    public void setUp() throws CreationException {
        area = new Sitting("Test area", 2, 3);
        Event e = new TheatreEvent("name", "title", 3, "desc", "author", "direc", 0, List.of("actor"));
        Performance p = new Performance(e, LocalDateTime.now());
        occupancy = new PerformanceSitting(area, 5, p);
        lp = Arrays.asList(area.getSeat(1, 1), area.getSeat(2, 2), area.getSeat(1, 2), area.getSeat(2, 3));
    }

    /** Ocupies all seats and checks the available and occupied positions */
    @Test
    public void occupySeats() {
        // Seat row=1 col=3 has been disabled inside PerformanceSitting
        assertEquals(5, occupancy.getAvailablePositons());
        // System.out.println(occupancy);
        assertFalse(occupancy.occupy(area.getSeat(1, 3)));
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                if (i != 1 || j != 3)
                    assertTrue(occupancy.occupy(area.getSeat(i, j)));
            }
        }
        assertFalse(occupancy.occupy(lp.get(2)));
        assertEquals(5, occupancy.getOccupiedPossitions());
        assertEquals(0, occupancy.getAvailablePositons());
        assertTrue(occupancy.isSoldOut());
        assertTrue(occupancy.unoccupy(lp.get(0)));
        assertFalse(occupancy.isSoldOut());
    }

    /**
     * Unoccupies some seats
     */

    @Test
    public void unoccupySeats() {
        assertTrue(occupancy.occupy(lp.get(0)));
        assertFalse(occupancy.unoccupy(lp.get(1)));
        assertTrue(occupancy.occupy(lp.get(1)));
    }

    /**
     * Checks the availability of some seats
     */

    @Test
    public void availabilitySeats() {
        assertTrue(occupancy.occupy(lp.get(3)));
        assertTrue(occupancy.isOccupied(lp.get(3)));
        assertFalse(occupancy.isOccupied(lp.get(2)));
    }

}
