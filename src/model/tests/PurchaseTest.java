package model. tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model. acts.events.*;
import model. acts.performances.Performance;
import model. app.TheatreTickets;
import model. areas.*;
import model. acts.cycles.Cycle;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import model. exceptions.CreationException;
import model. operations.*;
import model. operations.payments.*;

import model. areas.positions.*;
import model. users.Customer;

/**
 * Tester for {@link Purchase}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PurchaseTest {
    Event e;
    Sitting aA;
    Standing aB;
    Customer c;
    CreditCard cc;
    Performance perf;
    Cycle cy;
    Operation op;

    /**
     * Set up for all variables used
     * 
     * @throws CreationException If any creation exception
     */
    @Before
    public void setUp() throws CreationException {
        aA = new Sitting("A", 3, 3);
        TheatreTickets.getInstance().addArea(aA, 50.);
        aB = new Standing("B", 10);
        TheatreTickets.getInstance().addArea(aB, 40.);
        e = new TheatreEvent("Hamlet part 1", "Hamlet part 1", 180, "Test1 theatre event", "Me", "Me", 0,
                Arrays.asList("Me"));
        e.addAreaPrice(aA, 5);
        e.addAreaPrice(aB, 3);
        TheatreTickets.getInstance().addEvent(e);
        perf = new Performance(e, LocalDateTime.now().plusDays(2));
        TheatreTickets.getInstance().addPerformance(perf);
        c = new Customer("name", "pwd");
        TheatreTickets.getInstance().addCustomer(c);
        cc = new CreditCard("1234123412341234");
        cy = new Cycle("Cycle", Set.of(e));
        TheatreTickets.getInstance().addCycle(cy);
        cy.setDiscount(aA, 0.5);
        cy.setDiscount(aB, 0.4);
    }

    /**
     * Tests the creating a class wrong.
     * 
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException If payment fails
     */
    @Test(expected = CreationException.class)
    public void testCreation() throws CreationException, OrderRejectedException {
        List<Position> pos = List.of(aA.getSeat(1, 1), aB.getAPosition());
        // Passing as arguments seats of different areas to get an exception. Payments
        // dont matter as an exception will raise before they are used
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // all are available
        op = new Purchase(c, perf, pos, null);
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // assert that nothing has changed
    }

    /**
     * Tests only with credit card payments
     *
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException payment fails ption
     */
    @Test
    public void testBuyOnlyWithCredCard() throws CreationException, OrderRejectedException {
        // Sitting area
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        op = new Purchase(c, perf, pos, Set.of(cc));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertEquals(e.getPrice(aA) * 3, op.getRevenue(), 0.000001); // Check the revenue

        // Standing area
        pos = Collections.nCopies(5, aB.getAPosition());
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // All the positions available
        op = new Purchase(c, perf, pos, Set.of(cc));
        assertFalse(perf.availableCapacity(aB, aB.getCapacity()));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity() - 5)); // 5 positions now occupied
        assertEquals(e.getPrice(aB) * 5, op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Buying with annual pass
     * 
     * @throws CreationException      Error in creation
     * @throws OrderRejectedException Order couldnt be made
     */
    @Test
    public void testBuyWithAnnualPass() throws CreationException, OrderRejectedException {
        AnnualPass ap = new AnnualPass(aA);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf));
        op = new Purchase(c, perf, pos, Set.of(ap, cc));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertTrue(ap.isBought(perf));
        assertEquals(e.getPrice(aA) * 2 + ap.getPrice(), op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Buying with cycle pass
     * 
     * @throws CreationException      Error in creation
     * @throws OrderRejectedException Order couldnt be made
     */
    @Test
    public void testBuyWithCyclePass() throws CreationException, OrderRejectedException {
        CyclePass cp = new CyclePass(aA, cy);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(cp.isBought(perf));
        op = new Purchase(c, perf, pos, Set.of(cp, cc));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertTrue(cp.isBought(perf));
        assertEquals(e.getPrice(aA) * (2 + (1 - cp.getDiscount())), op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Buying with various annual pass
     * 
     * @throws CreationException      Error in creation
     * @throws OrderRejectedException Order couldnt be made
     */
    @Test
    public void testBuyWithMultiplePasses() throws CreationException, OrderRejectedException {
        CyclePass cp = new CyclePass(aA, cy);
        AnnualPass ap = new AnnualPass(aA);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf));
        assertFalse(cp.isBought(perf));
        op = new Purchase(c, perf, pos, Set.of(ap, cp, cc));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertTrue(ap.isBought(perf));
        assertTrue(cp.isBought(perf));
        assertEquals(e.getPrice(aA) * (1 + cp.getDiscount()) + ap.getPrice(), op.getRevenue(), 0.000001); // Check the
                                                                                                          // revenue
    }

    /**
     * Resets the instance of theatre
     */
    @After
    public void reset() {
        TheatreTickets.restore();
    }
}
