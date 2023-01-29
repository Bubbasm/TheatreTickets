package model.main;

import model.app.*;
import model.areas.*;
import model.enums.*;
import model.operations.payments.*;
import model.users.*;
import model.exceptions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import model.acts.events.*;
import model.acts.performances.*;
import model.acts.cycles.*;

/**
 * Non interactive demonstrator for the application
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Main {
    /**
     * Entry point of the main program
     * 
     * @param args None
     * @throws CreationException If any invalid parameter is passed
     * @throws IOException       Input Output error
     */
    public static void main(String[] args) throws CreationException, IOException {
        TheatreTickets instance = null;
        List<Area> areas = new ArrayList<>(List.of(new Composite("Left area"), new Standing("Front left", 40),
                new Sitting("Back left", 10, 5), new Composite("Right area"), new Standing("Front right", 30),
                new Sitting("Back right", 5, 10)));
        ((Composite) areas.get(0)).addAreas(areas.get(1), areas.get(2));
        ((Composite) areas.get(3)).addAreas(areas.get(4), areas.get(5));

        try {
            // Try to load the theatre from a file
            TheatreTickets.loadFrom("data/nonInteractiveDemonstrator.save");
            instance = TheatreTickets.getInstance();

            // updates list of areas used in the main
            for (int i = 0; i < areas.size(); i++) {
                // We are collecting non composite areas from the Theatre Tickets retrieved
                // instance because the ones in the list are different from the ones in the
                // TheatreTickets class when loading a .save file
                areas.set(i, instance.searchArea(areas.get(i).getName()));
            }

            //Save it
            System.out.println(">> Saving Theatre to file");
            TheatreTickets.saveTo("data/nonInteractiveDemonstrator.save");
        } catch (Exception ex) {
            // If the file doesn't exist, generate the theatre again and save it in a file
            System.out.println(">>Generating Theatre again (couldnt read it from file)");
            instance = TheatreTickets.getInstance();
            instance.addArea((Composite) areas.get(0));
            instance.addArea((Composite) areas.get(3));
            instance.addArea((NonComposite) areas.get(1), 100.);
            instance.addArea((NonComposite) areas.get(2), 150.);
            instance.addArea((NonComposite) areas.get(4), 100.);
            instance.addArea((NonComposite) areas.get(5), 150.);

            List<Event> events = List.of(
                    new TheatreEvent("Theatre Event 1", "Title for Theatre Event 1", 90, "Description of the event",
                            "Author", "Director", 0.15, List.of("Actor 1")),
                    new TheatreEvent("Theatre Event 2", "Title for Theatre Event 2", 45, "Description of the event",
                            "Author", "Director", 0.10, List.of("Actor 2")),
                    new TheatreEvent("Theatre Event 3", "Title for Theatre Event 3", 87, "Description of the event",
                            "Author", "Director", 0.00, List.of("Actor 3")),
                    new DanceEvent("Dance Event 1", "Title for Dance Event 1", 45, "Description of the event", "Author",
                            "Director", 0.91, "My orchestra", List.of("Dancer 1"), "Conductor"),
                    new DanceEvent("Dance Event 2", "Title for Dance Event 2", 200, "Description of the event",
                            "Author", "Director", 0.20, "My orchestra", List.of("Dancer 2"), "Conductor"),
                    new DanceEvent("Dance Event 3", "Title for Dance Event 3", 120, "Description of the event",
                            "Author", "Director", 0.40, "My orchestra", List.of("Dancer 3"), "Conductor"),
                    new MusicEvent("Music Event 1", "Title for Music Event 1", 30, "Description of the event", "Author",
                            "Director", 0.00, "My orchestra", "Soloist", "Program"),
                    new MusicEvent("Music Event 2", "Title for Music Event 2", 89, "Description of the event", "Author",
                            "Director", 0.15, "My orchestra", "Soloist", "Program"),
                    new MusicEvent("Music Event 3", "Title for Music Event 3", 41, "Description of the event", "Author",
                            "Director", 0.80, "My orchestra", "Soloist", "Program"));

            List<Performance> performances = new ArrayList<>();
            int i = 0;
            for (Event e : events) {
                instance.addEvent(e);

                for (Area a : areas) {
                    if (a instanceof NonComposite) {
                        e.addAreaPrice((NonComposite) a, 2 * (i + 1));
                    }
                }

                performances.add(new Performance(e, LocalDateTime.now().plusDays(1)));
                performances.add(new Performance(e, LocalDateTime.now().plusDays(i + 9)));
                i++;
            }
            // performances.add(new Performance(events.get(0),
            // LocalDateTime.now().plusDays(1)));

            for (Performance p : performances) {
                instance.addPerformance(p);
            }

            List<Cycle> cycles = List.of(new Cycle("Theatre and dance cycle", Set.copyOf(events.subList(0, 6))),
                    new Cycle("Dance and music cycle", Set.copyOf(events.subList(3, 9))));

            for (Cycle c : cycles) {
                for (NonComposite nc : instance.getMainArea().getNonCompositeWithin()) {
                    c.setDiscount(nc, 0.3);
                }
                instance.addCycle(c);
            }

            instance.setHoursForReservation(8);
            instance.setMaxTicketsPerPurchase(10);
            instance.setMaxTicketsPerReservation(5);

            TheatreTickets.saveTo("data/nonInteractiveDemonstrator.save");
        } finally {
            // after getting our instance one way or another, we start the nonInteractive
            // demonstrator

            Customer juan = null, pedro = null;

            System.out.println("\n--------USER TASKS:------");

            // demonstrating register process
            System.out.println("\n>>Registering user \"Juan\" with password \"password1234\"");
            try {
                instance.register("Juan", "password1234");
            } catch (InvalidUsernameException e) {
            }
            System.out.println(">>Register process completed succesfully!");

            System.out.println("\n>>Registering user \"Juan\" with password \"password1234\" (again)");
            try {
                instance.register("Juan", "password1234");
            } catch (InvalidUsernameException e) {
                System.out.println(">>Register process failed!");
                System.err.println(e);
            }

            // demonstrating login process
            System.out.println("\n>>Login as \"Juan\" with a bad password");
            try {
                instance.login("Juan", "notHisPassword");
            } catch (InvalidLoginException e) {
                System.err.println(e);
            }

            System.out.println("\n>>Login as \"Juan\"");
            try {
                juan = (Customer) instance.login("Juan", "password1234");
            } catch (InvalidLoginException e) {
            }

            // demonstrating how to search
            System.out.println("\n>>Searching for events with \"1\":");
            List<Event> events = instance.searchEvent("1");
            for (Event e : events) {
                System.out.println(e);
            }

            System.out.println("\n>>Searching for performances with \"Event 1\" and of type THEATRE:");
            List<Performance> performances = instance.searchPerformance("Event 1", SearchFilter.THEATRE);
            for (Performance p : performances) {
                System.out.println(p);
            }

            System.out.println("\n>>Searching for performances with \"Theatre Event 1\":");
            performances = instance.searchPerformance("Theatre Event 1");
            for (Performance p : performances) {
                System.out.println(p);
            }

            System.out.println("\n>>Searching for cycles with \"Theatre\":");
            List<Cycle> cycles = instance.searchCycle("Theatre");
            for (Cycle c : cycles) {
                System.out.println(c);
            }

            // demonstrating how to buy
            CreditCard cc = new CreditCard("1234123412341234");
            System.out.println(
                    "\n>>Buying 20 tickets (more than permitted) for last searched performance with creditCard:");
            Sitting a = (Sitting) areas.get(2); // area selection will be done through the GUI

           
            Performance searchedPerf = performances.get(0);
            if (juan.buyTickets(searchedPerf, a, 20, Set.of(cc), Heuristic.CENTERED_LOW) == false) {
                System.err.println(">>Error buying the tickets!");
            }

            System.out.println("\n>>Buying 4 tickets for the last searched performance with creditCard:");
            /*
             * try{ juan.buyTickets(searchedPerf, a, 5, Set.of(new
             * CreditCard("1234123412341234")), Heuristic.CENTERED_LOW); } catch
             * (OperationException e){}
             */
            juan.buyTickets(searchedPerf, a, 4, Set.of(cc), Heuristic.CENTERED_LOW);
            System.out.println(">>Tickets bought succesfully! Printing PDFs now in the printedTickets folder.");

            // demonstrating how to buy a pass
            Standing a2 = (Standing) areas.get(1); // area selection will be done through the GUI
            System.out.println("\n>>Buying annual pass:\n>>Payment completed");
            juan.purchaseAnnualPass(a2, cc);
            System.out.println("\n>>Buying cycle pass:\n>>Payment completed");
            juan.purchaseCyclePass(cycles.iterator().next(), a2, cc);

            // demonstrating how to reserve and confirm/cancel
            System.out.println("\n>>Reserving 20 tickets (more than permitted):");
            if (juan.reserveTickets(searchedPerf, a2, 20) == false) {
                // This will be handled through exceptions
                System.err.println(">>Error buying the tickets!");
            }

            System.out.println("\n>>Reserving 3 tickets for the last searched performance:");
            juan.reserveTickets(searchedPerf, a2, 3);
            System.out.println(
                    ">>Reservation completed. You have " + instance.getHoursForReservation() + "h to confirm it");

            System.out.println("\n>>Cancel the reservation:");
            juan.cancelReservation(juan.getActiveReservations().iterator().next()); // There is only one active
                                                                                    // reservation
            System.out.println(">>Reservation canceled.");

            System.out.println("\n>>Reserving 3 tickets for the last searched performance: (again)");
            juan.reserveTickets(searchedPerf, a2, 3);
            System.out.println(
                    ">>Reservation completed. You have " + instance.getHoursForReservation() + "h to confirm it");

            // join all of juan's payments in one set
            Set<PaymentMethod> pms = new HashSet<>(juan.getPayments());
            pms.add(cc);

            System.out.println("\n>>Confirm the reservation paying with the bought passes:");
            juan.confirmReservation(pms, juan.getActiveReservations().iterator().next()); // There is only one active
                                                                                          // reservation
            System.out.println(">>Reservation confirmed! Printing PDFs now in the printedTickets folder.");

            System.out.println("\n>>Reserving 3 tickets for the last searched performance: (again)");
            juan.reserveTickets(searchedPerf, a2, 3);
            System.out.println(
                    ">>Reservation completed. You have " + instance.getHoursForReservation() + "h to confirm it");

            System.out.println(
                    "\n>>Trying to confirm the reservation paying with the bought passes (we can't, as we have already bought the performance):");
            if (juan.confirmReservation(pms, juan.getActiveReservations().iterator().next()) == false) {
                System.err.println(">>Error, the payments are not valid"); // Will be done using exceptions
            }

            System.out.println("\n--------MANAGER TASKS:------");

            // demonstrate how the manager logs in
            System.out.println("\n>>Log in as the manager: (admin, admin)");
            try {
                instance.login("admin", "admin");
            } catch (InvalidLoginException e1) {
            }
            System.out.println(">>Logged in succesfully");

            // demonstrating how to add new Events/Performances
            System.out.println("\n>>Adding a new event called \"new Theatre\":");
            Event newEvent = new TheatreEvent("new Theatre", "new", 5, "new", "new", "new", 0.0, List.of("new"));
            for (Area a3 : areas) {
                if (a3 instanceof NonComposite) {
                    newEvent.addAreaPrice((NonComposite) a3, 10);
                }
            }
            instance.addEvent(newEvent);
            System.out.println(">>Now the event shows when looking for \"Theatre\":");
            events = instance.searchEvent("Theatre");
            for (Event e : events) {
                System.out.println(e);
            }

            System.out.println("\n>>Adding a new performance for the new event:");
            instance.addPerformance(new Performance(newEvent, LocalDateTime.now().plusDays(1)));
            System.out.println(">>Now it shows when looking for \"new\":");
            performances = instance.searchPerformance("new");
            for (Performance p : performances) {
                System.out.println(p);
            }

            LocalDateTime now = LocalDateTime.now();
            // demonstrating disabling a seat
            System.out.println("\n>>Disable all the seats in the area \"Back Right\"");
            for (int i = 1; i <= ((Sitting) areas.get(5)).getRows(); i++) {
                for (int j = 1; j <= ((Sitting) areas.get(5)).getCols(); j++) {
                    if (instance.disableSeat((Sitting) areas.get(5), i, j, now.minusDays(2), now.plusDays(2))) {
                        // this check will be done with exceptions
                        System.out.println(">>Seat " + i + ", " + j + " disabled correctly");
                    } else {
                        System.err.println(">>Error disabling seat " + i + ", " + j);
                    }
                }
            }

            System.out.println("\n>>Try to buy seats in that area between those dates (we shouldn't be able)");
            if (juan.buyTickets(performances.iterator().next(), ((Sitting) areas.get(5)), 2, Set.of(cc),
                    Heuristic.FAR_FROM_OTHERS) == false) {
                System.err.println(">>Error buying tickets! No seats available");
            }

            // cannot demonstrate how to add areas since the theatre has already been
            // created.
            // instead we demonstrate that you cannot change them once you have prformances
            // scheduled
            System.out.println("\n>>Trying to add a new area to the theatre:");
            if (instance.addArea(new Sitting("Balcony", 3, 3), 20.0) == false) {
                System.err.println(">>Error, cannot modify the theatre after scheduling performances!");
            }

            // get the first performance of dance event 1 (only 9% capacity available
            // because of restrictions)
            searchedPerf = instance.searchPerformance("Dance Event 1").get(1);
            a2 = (Standing) areas.get(4);
            try {
                pedro = (Customer) instance.register("Pedro", "12345678");
            } catch (InvalidUsernameException e) {
            }

            // pedro buys all the tickets
            System.out.println("\n>>Pedro reserves all the tickets left in the theatre for some performance:");
            if (pedro.reserveTickets(searchedPerf, a2, 2)) {
                System.out.println(">>Reserved Succesfully");
            }
            if (pedro.reserveTickets(searchedPerf, (Standing) areas.get(1), 3)) {
                System.out.println(">>Reserved Succesfully");
            }
            if (pedro.reserveTickets(searchedPerf, (Sitting) areas.get(5), 4, Heuristic.FAR_FROM_OTHERS)) {
                System.out.println(">>Reserved Succesfully");
            }
            if (pedro.reserveTickets(searchedPerf, (Sitting) areas.get(2), 4, Heuristic.FAR_FROM_OTHERS)) {
                System.out.println(">>Reserved Succesfully");
            }

            // for (int i = 0; i < ((Sitting) areas.get(5)).getRows(); i++) {
            // for (int j = 0; j < ((Sitting) areas.get(5)).getRows(); j++) {
            // if (searchedPerf.availableSeat(((Sitting) areas.get(5)),
            // ((Sitting) areas.get(5)).getSeat(i + 1, j + 1)))
            // ;
            // instance.disableSeat(((Sitting) areas.get(5)), i + 1, j + 1,
            // LocalDateTime.now().minusYears(5),
            // LocalDateTime.now().plusYears(5));
            // }
            // }

            System.out.println("\n>>Juan tries to reserve tickets for the same performance");
            if (juan.reserveTickets(searchedPerf, a2, 3) == false) {
                System.out.println(">>No tickets left, entering waiting list");
                if (juan.enterWaitingList(searchedPerf)) {
                    System.out.println(">>Entered in waiting list correctly");
                }
            }

            searchedPerf.postponePerformance(searchedPerf.getDate().plusDays(2));

            System.out.println(
                    "\n>>The performance has been postponed, so Pedro gets notified (4 times, one per reservation):\n Notifications:");
            System.out.println(pedro.getNotifications());
            pedro.cancelReservation(pedro.getActiveReservations().iterator().next());

            System.out.println(
                    "\n>>Pedro has canceled one of his reservations, so Juan recieves a notification:\n Notifications:");
            System.out.println(juan.getNotifications());
            juan.deleteNotification(); // empty the notifications

            System.out.println("\n>>Now Juan buys tickets for that performance");
            if (juan.buyTickets(searchedPerf, a2, 2, Set.of(cc))) {
                System.out.println(">>Bought Succesfully");
            }

            System.out.println("\n>>Cancelling the performance. Making refunds");
            searchedPerf.cancelPerformance();

            System.out.println("\n>>The performance has been cancelled, so Juan gets notified:\n Notifications:");
            System.out.println(juan.getNotifications());

            // demonstrating theatre's attendance, per event, performance and area
            System.out.println("\n>>Statistics for the theatre:");
            System.out.println("Attendance:");
            for (Event e : instance.getEvents()) {
                System.out.println("\nEvent: \"" + e.getName() + "\"");
                for (Performance perf : e.getPerformances()) {
                    System.out.println(" Date: " + perf.getDate());
                    for (NonComposite n : instance.getMainArea().getNonCompositeWithin()) {
                        System.out.println(" Area: \"" + n.getName() + "\" -> " + perf.getAttendance(n));
                    }
                }
            }

            // demonstrating theatre's revenue categorized by per event, performance and
            // area
            System.out.println("\nRevenue: (note that Dance Event 1 has revenue 0.0 because it has been cancelled)");
            for (Event e : instance.getEvents()) {
                System.out.println("\nEvent: \"" + e.getName() + "\"");
                for (Performance perf : e.getPerformances()) {
                    System.out.println(" Date: " + perf.getDate() + " Status: " + perf.getStatus());
                    for (NonComposite n : instance.getMainArea().getNonCompositeWithin()) {
                        System.out.println(" Area: \"" + n.getName() + "\" -> " + perf.getRevenue(n));
                    }
                }
            }
            instance.logout();
        }
    }
}
