package model. tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;

import model. acts.performances.Performance;
import model. acts.events.*;

import org.junit.Before;

import model. app.TheatreTickets;
import model. areas.Sitting;
import model. exceptions.CreationException;
import model. operations.payments.*;

/**
 * Tester for {@link AnnualPass}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AnnualPassTest {
    Sitting areaA;
    Performance notValid;
    Performance valid;
    Event ev;
    AnnualPass pass;

    /**
     * Sets up parameters
     * 
     * @throws CreationException If any parameter is missing
     */
    @Before
    public void setUp() throws CreationException {
        areaA = new Sitting("Test", 5, 5);
        TheatreTickets.getInstance().addArea(areaA, 100.);
        ev = new TheatreEvent("Hamlet", "Hamlet", 180, "Test theatre event", "Me", "Me", 0, Arrays.asList("Me"));
        notValid = new Performance(ev, LocalDateTime.now().plusYears(2));
        valid = new Performance(ev, LocalDateTime.now().plusDays(1));
        pass = new AnnualPass(areaA);
        TheatreTickets.getInstance().addEvent(ev);
        TheatreTickets.getInstance().addPerformance(valid);
        TheatreTickets.getInstance().addPerformance(notValid);
    }

    /** Test if pass is valid */
    @Test
    public void testPass() {
        assertEquals(100., pass.getPrice(), 0.0001);

        assertFalse(pass.isBought(valid));
        assertTrue(pass.markAsBought(valid));
        assertTrue(pass.isBought(valid));
        assertFalse(pass.markAsBought(valid)); // Cannot buy two tickets for the same performance

        assertFalse(pass.isBought(notValid));
        assertFalse(pass.markAsBought(notValid)); // Not valid as is from two years ago
        assertFalse(pass.isBought(notValid));
    }
}
