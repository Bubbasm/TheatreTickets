package model. tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model. acts.events.*;
import model. acts.performances.*;
import model. app.TheatreTickets;
import model. areas.*;
import model. exceptions.*;
import model. operations.*;
import model. operations.payments.*;
import model. users.*;
import es.uam.eps.padsof.telecard.*;
import es.uam.eps.padsof.tickets.*;

/**
 * Tester for {@link Ticket}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TicketTest {
    Standing stan;
    Sitting sit;
    Event e;
    Performance p;
    Customer u;
    CreditCard cc;
    Set<PaymentMethod> pms;

    /**
     * Sets up variables
     * 
     * @throws CreationException If any invalid parameter
     */
    @Before
    public void setUp() throws CreationException {
        stan = new Standing("Area A", 10);
        sit = new Sitting("Area B", 10, 5);
        e = new TheatreEvent("Hamlet part 1", "Hamlet part 1", 180, "Test1 theatre event", "Me", "Me", 0,
                Arrays.asList("Me"));
        e.addAreaPrice(stan, 5);
        e.addAreaPrice(sit, 7);
        p = new Performance(e, LocalDate.of(2021, 1, 01).atStartOfDay());
        u = new Customer("user1", "1234");
        cc = new CreditCard("1111222233334444");
        TheatreTickets.getInstance().addArea(stan, 100.);
        TheatreTickets.getInstance().addArea(sit, 200.);
        TheatreTickets.getInstance().addEvent(e);
        TheatreTickets.getInstance().addPerformance(p);
        TheatreTickets.getInstance().addCustomer(u);
        u.purchaseAnnualPass(sit, cc);
        u.purchaseAnnualPass(stan, cc);
        pms = new HashSet<>(u.getPayments());
        pms.add(cc);
    }

    /**
     * Tests the creation of a tickets with sitting position
     * 
     * @throws CreationException      if any invalid parameter
     * @throws OrderRejectedException if couldnt complete payment
     */
    @Test
    public void testSittingTickets() throws CreationException, OrderRejectedException {
        Purchase purch = new Purchase(u, p, List.of(sit.getSeat(1, 1), sit.getSeat(1, 2), sit.getSeat(1, 3)), pms);
        // purchase each ticket with a different payment
        List<Ticket> tickets = List.copyOf(purch.getTickets());

        for (Ticket t : tickets) {
            if (t.getPayment() instanceof Pass) {
                assertEquals(t.getPrice(), 0, 0.00001);
            } else {
                assertEquals(t.getPrice(), e.getPrice(sit), 0.00001);
            }
        }
    }

    /**
     * Tests the creation of a tickets with sitting position
     * 
     * @throws CreationException      if any invalid parameter
     * @throws OrderRejectedException if couldnt complete payment
     */
    @Test
    public void testStandingTickets() throws CreationException, OrderRejectedException {
        Purchase purch = new Purchase(u, p, Collections.nCopies(3, stan.getAPosition()), pms);
        // purchase each ticket with a different payment
        List<Ticket> tickets = List.copyOf(purch.getTickets());

        for (Ticket t : tickets) {
            if (t.getPayment() instanceof Pass) {
                assertEquals(t.getPrice(), 0, 0.00001);
            } else {
                assertEquals(e.getPrice(stan), t.getPrice(), 0.00001);
            }
        }
    }

    /**
     * Prints tickets to pdf
     * 
     * @throws CreationException             invalid parameter
     * @throws OrderRejectedException        cannot pay
     * @throws NonExistentFileException      file does not exist
     * @throws UnsupportedImageTypeException image is not supported
     */
    @Test
    public void testPrint()
            throws CreationException, OrderRejectedException, NonExistentFileException, UnsupportedImageTypeException {
        Purchase purch = new Purchase(u, p, Collections.nCopies(3, stan.getAPosition()), pms);
        Purchase purch2 = new Purchase(u, p, List.of(sit.getSeat(1, 1), sit.getSeat(1, 2), sit.getSeat(1, 3)), pms);
        for (Ticket t : purch.getTickets()) {
            t.print();
        }
        for (Ticket t : purch2.getTickets()) {
            t.print();
        }
    }
}
