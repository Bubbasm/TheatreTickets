package model. tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import model. acts.events.*;
import model. acts.performances.*;
import model. app.*;
import model. operations.*;
import model. operations.payments.*;
import model. areas.*;
import model. areas.positions.*;
import model. acts.cycles.Cycle;
import model. enums.ReservationStatus;
import model. users.Customer;

import es.uam.eps.padsof.telecard.OrderRejectedException;
import model. exceptions.CreationException;

/**
 * Tester for {@link Reservation}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ReservationTest {
    Event e;
    Sitting aA;
    Standing aB;
    Customer c;
    CreditCard cc;
    Performance perf;
    Cycle cy;
    Reservation op;

    /**
     * Setup method, initilizes everything
     * 
     * @throws CreationException shouldnt be thrown
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
        perf = new Performance(e, LocalDateTime.now().plusDays(1));
        TheatreTickets.getInstance().addPerformance(perf);
        c = new Customer("name", "pwd");
        TheatreTickets.getInstance().addCustomer(c);
        cc = new CreditCard("1234123412341234");
        cy = new Cycle("Cycle", Set.of(e));
        TheatreTickets.getInstance().addCycle(cy);
        cy.setDiscount(aA, 0.5);
        cy.setDiscount(aB, 0.4);
        TheatreTickets.getInstance().setHoursForReservation(5);
    }

    /**
     * Creates a wrong reservation
     *
     * @throws CreationException      Expects this exception
     * @throws OrderRejectedException If payment fails ption
     * 
     */
    @Test(expected = CreationException.class)
    public void testCreation() throws CreationException, OrderRejectedException {
        List<Position> pos = List.of(aA.getSeat(1, 1), aB.getAPosition());
        // Passing as arguments seats of different areas to get an exception. Payments
        // dont matter as an exception will raise before they are used
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // all are available
        op = new Reservation(c, perf, pos);
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // assert that nothing has changed
    }

    /**
     * Confirms reservation with credit card
     * 
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException If payment fails ption
     */
    @Test
    public void testConfirmWithCredCard() throws CreationException, OrderRejectedException {
        // Sitting area
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.confirm(Set.of(cc));
        assertEquals(ReservationStatus.CONFIRMED, op.getStatus());
        assertEquals(e.getPrice(aA) * 3, op.getRevenue(), 0.000001); // Check the revenue

        // Standing area
        pos = Collections.nCopies(5, aB.getAPosition());
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // All the positions available
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableCapacity(aB, aB.getCapacity()));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity() - 5)); // 5 positions wew occupied
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.confirm(Set.of(cc));
        assertEquals(ReservationStatus.CONFIRMED, op.getStatus());
        assertEquals(e.getPrice(aA) * 3, op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Confirms with annual pass
     * 
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException If payment fails
     */
    @Test
    public void testConfirmWithAnnualPass() throws CreationException, OrderRejectedException {
        // Annual Pass
        AnnualPass ap = new AnnualPass(aA);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf));
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf)); // hasnt been confirmed yet
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.confirm(Set.of(ap, cc));
        assertTrue(ap.isBought(perf));
        assertEquals(e.getPrice(aA) * 2 + ap.getPrice(), op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Confirms with cycle pass
     * 
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException If payment fails
     */
    @Test
    public void testConfirmWithCyclePass() throws CreationException, OrderRejectedException {
        CyclePass cp = new CyclePass(aA, cy);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(cp.isBought(perf)); // Havent been confirmed yet
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(cp.isBought(perf)); // Havent been confirmed yet
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.confirm(Set.of(cp, cc));
        assertTrue(cp.isBought(perf));
        assertEquals(e.getPrice(aA) * (2 + (1 - cp.getDiscount())), op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Confirms with various passes
     * 
     * @throws CreationException      Expected this exception
     * @throws OrderRejectedException If payment fails
     */
    @Test
    public void testConfirmWithMultiplePasses() throws CreationException, OrderRejectedException {
        CyclePass cp = new CyclePass(aA, cy);
        AnnualPass ap = new AnnualPass(aA);
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf));
        assertFalse(cp.isBought(perf));
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertFalse(ap.isBought(perf)); // Havent been confirmed yet
        assertFalse(cp.isBought(perf));
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.confirm(Set.of(ap, cp, cc));
        assertTrue(ap.isBought(perf));
        assertTrue(cp.isBought(perf));
        assertEquals(e.getPrice(aA) * (1 + cp.getDiscount()) + ap.getPrice(), op.getRevenue(), 0.000001); // Check the
                                                                                                          // revenue
    }

    /**
     * Cancels reservations
     * 
     * @throws CreationException Expected this exception
     */
    @Test
    public void testCancelReservation() throws CreationException {
        // Sitting area
        List<Position> pos = List.of(aA.getSeat(1, 1), aA.getSeat(1, 2), aA.getSeat(1, 3));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2))); // Now they are not available
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.cancel();
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 2))); // Available seats again
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 3)));
        assertEquals(ReservationStatus.CANCELLED, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        // Standing area
        pos = Collections.nCopies(5, aB.getAPosition());
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // All the positions available
        op = new Reservation(c, perf, pos);
        assertFalse(perf.availableCapacity(aB, aB.getCapacity()));
        assertTrue(perf.availableCapacity(aB, aB.getCapacity() - 5)); // 5 positions wew occupied
        assertEquals(ReservationStatus.ACTIVE, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue

        op.cancel();
        assertTrue(perf.availableCapacity(aB, aB.getCapacity())); // All the positions available again
        assertEquals(ReservationStatus.CANCELLED, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Checks if a reservation is expired
     * 
     * @throws CreationException Expected this exception
     * 
     */
    @Test
    public void testIsExpired() throws CreationException {
        List<Position> pos = List.of(aA.getSeat(1, 1));
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1))); // Available seat
        op = new Reservation(c, perf, pos, LocalDateTime.now().minusDays(2)); // Expired reservation
        assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1))); // Now it is not available
        assertEquals(ReservationStatus.EXPIRED, op.getStatus());

        op.isExpired();
        assertTrue(perf.availableSeat(aA, aA.getSeat(1, 1))); // Available again
        assertEquals(ReservationStatus.EXPIRED, op.getStatus());
        assertEquals(0, op.getRevenue(), 0.000001); // Check the revenue
    }

    /**
     * Checks if expiring date is accurate
     * 
     * @throws CreationException Expected this exception
     * 
     */
    @Test
    public void testGetExpiringDate() throws CreationException {
        // Note that the performance's date is LocalDateTime.now().plusDays(1)

        int hours = TheatreTickets.getInstance().getHoursForReservation();
        List<Position> pos = List.of(aA.getSeat(1, 1));

        // expiringDate = reservationDate + hoursForReservation
        op = new Reservation(c, perf, pos, LocalDateTime.now().minusDays(2));
        assertEquals(LocalDateTime.now().minusDays(2).plusHours(hours).truncatedTo(ChronoUnit.MINUTES),
                op.getExpiringDate().truncatedTo(ChronoUnit.MINUTES));

        // expiringDate = performanceDate - hoursForReservation (reservation made really
        // close to the performance's date)
        op = new Reservation(c, perf, pos, LocalDateTime.now().plusHours(18)); // Will have 1h to confirm the
                                                                               // reservation
        assertEquals(perf.getDate().minusHours(hours).truncatedTo(ChronoUnit.MINUTES),
                op.getExpiringDate().truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Resets app instance
     */
    @After
    public void reset() {
        TheatreTickets.restore();
    }
}
