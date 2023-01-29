package model. tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import model. acts.events.*;
import model. areas.*;
import model. acts.cycles.Cycle;
import model. exceptions.CreationException;

/**
 * Tester for {@link Cycle}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CycleTest {
    /**
     * Tests cycle, applying many discounts
     * 
     * @throws CreationException If any invalid parameters
     */
    @Test
    public void testCycle() throws CreationException {
        Event e1 = new TheatreEvent("Hamlet", "Hamlet", 180, "Test1 theatre event", "Me", "Me", 0, Arrays.asList("Me"));
        Event e2 = new DanceEvent("Ballet", "Ballet", 100, "Test2 dance event", "Me", "Me", 0, "My orchestra",
                Arrays.asList("Me"), "Me");
        Sitting a1 = new Sitting("Sitting", 3, 3);
        Standing a2 = new Standing("Standing", 30);
        Cycle c = new Cycle("Cycle 1", Set.of(e1, e2));

        c.setDiscount(a1, 0.05);
        c.setDiscount(a2, 0.3);
        assertEquals(0.05, c.getDiscount(a1), 0.0001);
        assertEquals(0.3, c.getDiscount(a2), 0.0001);
    }
}
