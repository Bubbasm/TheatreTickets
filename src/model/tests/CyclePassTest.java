package model. tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import model. acts.events.Event;
import model. acts.events.TheatreEvent;
import model. acts.performances.Performance;
import model. app.TheatreTickets;
import model. areas.Sitting;
import model. acts.cycles.Cycle;
import model. exceptions.CreationException;
import model. operations.payments.*;

/**
 * Tester for {@link CyclePass}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CyclePassTest {
    Sitting areaA;
    Sitting areaB;
    Event e1, e2, e3;
    CyclePass passA, passB;
    Cycle c;
    Performance p, notValid;

    /**
     * Sets up variables
     *
     * @throws CreationException If any parameters are invalid
     */
    @Before
    public void setUp() throws CreationException {
        areaA = new Sitting("Test", 5, 5);
        areaB = new Sitting("Test2", 5, 5);
        TheatreTickets.getInstance().addArea(areaA, 100.);
        e1 = new TheatreEvent("Hamlet", "Hamlet", 180, "Test theatre event", "Me", "Me", 0, Arrays.asList("Me"));
        e1.addAreaPrice(areaA, 20);
        e1.addAreaPrice(areaB, 30);
        e2 = new TheatreEvent("Hamlet2", "Hamlet2", 180, "Test theatre event", "Me", "Me", 0, Arrays.asList("Me"));
        e2.addAreaPrice(areaA, 10);
        e2.addAreaPrice(areaB, 15);
        p = new Performance(e1, LocalDateTime.now().plusDays(2));
        e3 = new TheatreEvent("Not in the cycle", "Not in the cycle", 180, "Test ", "Me", "Me", 0, Arrays.asList("Me"));
        notValid = new Performance(e3, LocalDateTime.now().plusDays(2));
        c = new Cycle("Test Cycle", Set.of(e1, e2));
        c.setDiscount(areaA, 0.1);
        c.setDiscount(areaB, 0.2);
        passA = new CyclePass(areaA, c);
        passB = new CyclePass(areaB, c);
        TheatreTickets.getInstance().addEvent(e1);
        TheatreTickets.getInstance().addEvent(e2);
    }

    /** Tests cycle pass */
    @Test
    public void testPass() {
        assertEquals(27., passA.getPrice(), 0.0001);
        assertEquals(36., passB.getPrice(), 0.0001);

        assertFalse(passA.isBought(p));
        assertTrue(passA.markAsBought(p));
        assertTrue(passA.isBought(p));
        assertFalse(passA.markAsBought(p)); // Cannot buy two tickets for the same event

        assertFalse(passA.isBought(notValid));
        assertFalse(passA.markAsBought(notValid)); // Not valid as it is not in the cycle
        assertFalse(passA.isBought(notValid));
    }
}
