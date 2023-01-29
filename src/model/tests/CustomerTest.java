package model.tests;

import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDateTime;
import org.junit.*;

import model.acts.performances.*;
import model.acts.events.*;
import model.app.*;
import model.areas.*;
import model.acts.cycles.Cycle;
import model.enums.*;
import model.exceptions.*;
import model.users.*;
import model.operations.Reservation;
import model.operations.payments.*;

/**
 * Tester for {@link Customer}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CustomerTest {

    Customer c, c2;
    TheatreTickets instance;
    TheatreEvent eve;
    Performance perf;
    Cycle cyc;
    Sitting aA;
    Standing aB;
    PaymentMethod pm;

    /**
     * Sets up varibles
     * 
     * @throws CreationException If any parameter is invalid
     */
    @Before
    public void setUp() throws CreationException {
        instance = TheatreTickets.getInstance();
        instance.setMaxTicketsPerPurchase(6);
        instance.setMaxTicketsPerReservation(3);
        eve = new TheatreEvent("Hamlet part 2", "Hamlet part 2", 180, "Test2 theatre event", "Me", "Me", 0,
                Arrays.asList("Me"));
        c = new Customer("Name", "Password");
        c2 = new Customer("Name2", "Password");
        aA = new Sitting("Sitting", 4, 4);
        aB = new Standing("Standing", 6);
        instance.addArea(aA, 20.);
        instance.addArea(aB, 30.0);
        eve.addAreaPrice(aA, 2);
        eve.addAreaPrice(aB, 3);
        perf = new Performance(eve, LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        cyc = new Cycle("Cycle", Set.of(eve));
        cyc.setDiscount(aA, 0.5);
        cyc.setDiscount(aB, 0.5);
        pm = new CreditCard("1111222233334444");
        instance.addCycle(cyc);
        instance.addPerformance(perf);
        instance.addEvent(eve);
    }

    /** Looks for far from other seats, following a certain algorithm */
    @Test
    public void farFromOthers() {
        perf.occupy(aA, List.of(aA.getSeat(1, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        assertTrue(c.buyTickets(perf, aA, 5, Set.of(pm), Heuristic.FAR_FROM_OTHERS));
        // System.out.println(perf.areaOccupancy(aA));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 3)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 3)));
    }

    /**
     * Looks for lower and centered seats at the same time, following a certain
     * algorithm
     */
    @Test
    public void lowerHeuristic() {
        perf.occupy(aA, List.of(aA.getSeat(2, 2)));
        assertTrue(c.buyTickets(perf, aA, 5, Set.of(pm), Heuristic.CENTERED_LOW));
        // System.out.println("\n" + perf.areaOccupancy(aA));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(1, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(1, 2)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(1, 3)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(1, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 3)));
        assertTrue(c.buyTickets(perf, aA, 2, Set.of(pm), Heuristic.CENTERED_LOW));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 4)));
        /** No more seats in lower section */
        assertFalse(c.buyTickets(perf, aA, 1, Set.of(pm), Heuristic.CENTERED_LOW));
    }

    /**
     * Looks for middle and centered seats at the same time, following a certain
     * algorithm
     */
    @Test
    public void midHeuristic() {
        perf.occupy(aA, List.of(aA.getSeat(2, 2)));
        assertTrue(c.buyTickets(perf, aA, 5, Set.of(pm), Heuristic.CENTERED_MID));
        // System.out.println("\n" + perf.areaOccupancy(aA));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 3)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(2, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 2)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 3)));
        assertTrue(c.buyTickets(perf, aA, 2, Set.of(pm), Heuristic.CENTERED_MID));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 4)));
        /** No more seats in lower section */
        assertFalse(c.buyTickets(perf, aA, 1, Set.of(pm), Heuristic.CENTERED_MID));
    }

    /**
     * Looks for middle and centered seats at the same time, following a certain
     * algorithm
     */
    @Test
    public void highHeuristic() {
        perf.occupy(aA, List.of(aA.getSeat(2, 2)));
        assertTrue(c.buyTickets(perf, aA, 6, Set.of(pm), Heuristic.CENTERED_HIGH));
        // System.out.println(perf.areaOccupancy(aA));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 2)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 3)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(3, 4)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 2)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 3)));
        assertTrue(c.buyTickets(perf, aA, 2, Set.of(pm), Heuristic.CENTERED_HIGH));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 1)));
        // assertFalse(perf.availableSeat(aA, aA.getSeat(4, 4)));
        /** No more seats in lower section */
        assertFalse(c.buyTickets(perf, aA, 1, Set.of(pm), Heuristic.CENTERED_HIGH));
    }

    /** Buys a set of seats */
    @Test
    public void buyTicketsGivenSeats() {
        assertTrue(c.buyTickets(perf, aA, Set.of(pm), Set.of(aA.getSeat(1, 1), aA.getSeat(2, 2))));
        assertTrue(c.buyTickets(perf, aA, Set.of(pm), Set.of(aA.getSeat(3, 3), aA.getSeat(4, 4))));
        assertFalse(c.buyTickets(perf, aA, Set.of(pm), Set.of(aA.getSeat(1, 1), aA.getSeat(3, 2))));
        assertTrue(c.buyTickets(perf, aA, Set.of(pm), Set.of(aA.getSeat(3, 1), aA.getSeat(3, 2))));
    }

    /** Buys a numeber of positions */
    @Test
    public void buyTicketsStanding() {
        assertTrue(c.buyTickets(perf, aB, 5, Set.of(pm)));
        assertFalse(c.buyTickets(perf, aB, 2, null));
        assertFalse(c.buyTickets(perf, aB, 2, Set.of(new AnnualPass(aB)))); // Only one pass, and no credit card
    }

    /** Reserves seats */
    @Test
    public void reserverProcessGivenSeats() {
        assertFalse(c.reserveTickets(perf, aA,
                Set.of(aA.getSeat(1, 1), aA.getSeat(2, 2), aA.getSeat(3, 3), aA.getSeat(4, 4))));
        assertTrue(c.reserveTickets(perf, aA, Set.of(aA.getSeat(1, 1), aA.getSeat(2, 2))));
        assertFalse(c.reserveTickets(perf, aA, Set.of(aA.getSeat(1, 1), aA.getSeat(3, 2))));
        assertTrue(c.reserveTickets(perf, aA, Set.of(aA.getSeat(3, 3), aA.getSeat(4, 4))));
        assertTrue(c.reserveTickets(perf, aA, Set.of(aA.getSeat(4, 3), aA.getSeat(3, 4))));
        List<Reservation> reserv = List.copyOf(c.getReservations());
        assertTrue(c.confirmReservation(Set.of(pm), reserv.get(0)));
        assertTrue(c.cancelReservation(reserv.get(1)));
        assertFalse(c2.cancelReservation(reserv.get(2)));
    }

    /** Reserves positions in standing area */
    @Test
    public void reserverProcessStanding() {
        assertFalse(c.reserveTickets(perf, aB, 4));
        assertTrue(c.reserveTickets(perf, aB, 2));
        assertTrue(c.reserveTickets(perf, aB, 3));
        assertTrue(c.reserveTickets(perf, aB, 1));
        List<Reservation> reserv = List.copyOf(c.getReservations());
        assertEquals(3, reserv.size());
        assertTrue(c.confirmReservation(Set.of(pm), reserv.get(0)));
        assertTrue(c.cancelReservation(reserv.get(1)));
        // cannot cancel a reservation which doesnt belong to me
        assertFalse(c2.cancelReservation(reserv.get(2)));
    }

    /**
     * Checks passes
     * 
     * @throws CreationException If invalid arguments when creating credit card
     */
    @Test
    public void passes() throws CreationException {
        CreditCard cc = new CreditCard("1234567890123456");
        assertTrue(c.purchaseAnnualPass(aA, cc));
        assertEquals(1, c.getPayments().size());
        assertTrue(c2.purchaseAnnualPass(aA, cc));
        assertTrue(c2.purchaseCyclePass(cyc, aB, cc));
        assertEquals(2, c2.getPayments().size());
    }

    /** Checks for notifications in a customer */
    @Test
    public void notifs() {
        assertFalse(c.enterWaitingList(perf));
        assertTrue(c2.buyTickets(perf, aB, 6, Set.of(pm)));
        assertFalse(c.enterWaitingList(perf));
        assertTrue(c2.buyTickets(perf, aA, 6, Set.of(pm), Heuristic.FAR_FROM_OTHERS));
        assertTrue(c2.buyTickets(perf, aA, 6, Set.of(pm), Heuristic.FAR_FROM_OTHERS));
        assertTrue(c2.reserveTickets(perf, aA, 2, Heuristic.FAR_FROM_OTHERS));
        assertTrue(c2.reserveTickets(perf, aA, 2, Heuristic.FAR_FROM_OTHERS));
        assertTrue(c.enterWaitingList(perf));
        assertTrue(c2.cancelReservation(c2.getReservations().iterator().next()));
        // cancel the first reservation, whichever it is
        perf.cancelPerformance();
        assertEquals(1, c.getNumberOfNotifications());
        // should recieve 5 nofifications as 6 operations had been made but 1 was
        // canceled
        assertEquals(5, c2.getNumberOfNotifications());
        c2.deleteNotification();
        assertEquals(0, c2.getNumberOfNotifications());
    }

    /** Resets the TheatreTickets instance */
    @After
    public void reset() {
        TheatreTickets.restore();
    }
}
