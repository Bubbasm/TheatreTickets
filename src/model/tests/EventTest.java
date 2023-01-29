package model. tests;

import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDate;
import org.junit.*;

import model. acts.performances.*;
import model. acts.events.*;
import model. app.*;
import model. areas.*;
import model. acts.cycles.Cycle;
import model. exceptions.*;
import model. users.*;
import model. operations.payments.*;

/**
 * Header for {@link Event}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EventTest {
    TheatreTickets instance;
    TheatreEvent the; // no restriction
    DanceEvent dan; // 20% restriction
    MusicEvent mus; // no restriction
    List<Event> eves; // a mere list with the,dan,mus
    List<Performance> performances; // performances created. 0->the, 1->dan, 2->mus, 3->the, 4->the
    Cycle cys; // of theatre event and dance event
    Standing aA; // with capp 100
    Sitting aB; // with cap 10x8 = 80

    /**
     * Sets up variables used
     * 
     * @throws CreationException If any invalid parameter
     */
    @Before
    public void setUp() throws CreationException {

        instance = TheatreTickets.getInstance();
        instance.setMaxTicketsPerPurchase(10);
        instance.setMaxTicketsPerReservation(7);
        aA = new Standing("Test A", 100);
        aB = new Sitting("Test B", 10, 8);
        instance.addArea(aA, 20.);
        instance.addArea(aB, 16.);
        the = new TheatreEvent("Hamlet part 1", "Hamlet part 1", 180, "Test1 theatre event", "Me", "Me", 0,
                Arrays.asList("Me"));
        dan = new DanceEvent("Ballet 1", "Ballet 1", 100, "Test1 dance event", "Me", "Me", 0.2, "My orchestra",
                Arrays.asList("Me"), "Me");
        mus = new MusicEvent("Mozart concert 1", "Mozart concert 1", 90, "Test1 music event", "Me", "Me", 0,
                "My orchestra", "Me", "Song1, then song2");

        eves = new ArrayList<>();
        eves.add(the);
        eves.add(dan);
        eves.add(mus);
        cys = new Cycle("Example Cycle", Set.of(the, dan));

        for (int i = 0; i < 3; i++) {
            instance.addEvent(eves.get(i));
            // Their annual passes cost twice as much
            eves.get(i).addAreaPrice(aA, 10.);
            eves.get(i).addAreaPrice(aB, 8.);
        }

        performances = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            performances.add(new Performance(eves.get(i), LocalDate.of(2021, 1 + i, 1).atStartOfDay()));
            instance.addPerformance(performances.get(i));
        }
        performances.add(new Performance(the, LocalDate.of(2021, 5, 1).atStartOfDay()));
        instance.addPerformance(performances.get(3));
        performances.add(new Performance(the, LocalDate.of(2021, 8, 1).atStartOfDay()));
        instance.addPerformance(performances.get(4));
    }

    /**
     * Calculates the revenue of an event
     * 
     * @throws CreationException If any invalid parameter in credit card
     */
    @Test
    public void revenueCalculation() throws CreationException {
        Customer c = new Customer("Name", "Password");
        CreditCard cc = new CreditCard("1111222233334444");
        c.buyTickets(performances.get(0), aA, 5, Set.of(cc));
        System.out.println(performances.get(0).getAttendance(aA));
        assertEquals(50.0, the.getRevenue(aA), 0.01); // 5 tickets have been bought
        c.purchaseAnnualPass(aA, cc);
        c.buyTickets(performances.get(3), aA, 2, Set.of(cc, c.getPayments().iterator().next()));
        assertEquals(80.0, the.getRevenue(aA), 0.01);
        c.buyTickets(performances.get(4), aA, 1, c.getPayments());
        assertEquals(80.0, the.getRevenue(aA), 0.01);
        c.buyTickets(performances.get(0), aA, 1, c.getPayments());
        assertEquals(80.0, the.getRevenue(aA), 0.01);
        // Balance is still 80 € because the annual pass is used for only this event
        // until now

        c.buyTickets(performances.get(1), aA, 1, c.getPayments());
        // Revenue of the theatre event must decrease because
        // the revenue of the annual pass has been split among other events
        assertEquals(75.0, the.getRevenue(aA), 0.01);
        c.buyTickets(performances.get(2), aA, 1, c.getPayments());
        // Decreases even further when purchasing more tickets of OTHER events
        assertEquals(72.0, the.getRevenue(aA), 0.01);
    }

    /** Computes the real capacity after restriction */
    @Test
    public void capacityCalculation() {
        assertEquals(64, dan.getRestrictedCapacity(aB));
        // System.out.println(performances.get(1).getAttendance(aB));
        assertEquals(80, dan.getRestrictedCapacity(aA));
    }

    /** Resets TheatreTickets instance */
    @After
    public void reset() {
        TheatreTickets.restore();
    }
}
