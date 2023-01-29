package model.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.acts.performances.*;
import model.acts.events.*;
import model.app.*;
import model.areas.*;
import model.acts.cycles.Cycle;
import model.enums.*;
import model.exceptions.*;
import model.users.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Tester for {@link TheatreTickets}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TheatreTicketsTest {
    TheatreTickets instance;
    List<TheatreEvent> theatres;
    List<DanceEvent> dances;
    List<MusicEvent> musics;
    List<Event> events;
    List<Performance> performances;
    List<Cycle> cycles;
    Standing areaA;
    Sitting areaB;
    Composite areaC;
    Standing areaD;

    /**
     * Sets up an example of a Theatre ticket, creating all kinds of classes
     * 
     * @throws CreationException If any invalid parameter
     */
    @Before
    public void setUp() throws CreationException {
        instance = TheatreTickets.getInstance();

        areaA = new Standing("Test A", 100);
        areaD = new Standing("Test D", 100);
        areaB = new Sitting("Test B", 10, 8);
        areaC = new Composite("Test C");
        areaC.addAreas(areaD);

        theatres = Arrays.asList(
                new TheatreEvent("Hamlet part 1", "Hamlet part 1", 180, "Test1 theatre event", "Me", "Me", 0,
                        Arrays.asList("Me")),
                new TheatreEvent("Hamlet part 2", "Hamlet part 2", 180, "Test2 theatre event", "Me", "Me", 0,
                        Arrays.asList("Me")),
                new TheatreEvent("Hamlet part 3", "Hamlet part 3", 180, "Test3 theatre event", "Me", "Me", 0,
                        Arrays.asList("Me")));
        dances = Arrays.asList(
                new DanceEvent("Ballet 1", "Ballet 1", 100, "Test1 dance event", "Me", "Me", 0, "My orchestra",
                        Arrays.asList("Me"), "Me"),
                new DanceEvent("Ballet 2", "Ballet 2", 100, "Test2 dance event", "Me", "Me", 0, "My orchestra",
                        Arrays.asList("Me"), "Me"),
                new DanceEvent("Ballet 3", "Ballet 3", 100, "Test3 dance event", "Me", "Me", 0, "My orchestra",
                        Arrays.asList("Me"), "Me"));
        musics = Arrays.asList(
                new MusicEvent("Mozart concert 1", "Mozart concert 1", 90, "Test1 music event", "Me", "Me", 0,
                        "My orchestra", "Me", "Song1, then song2"),
                new MusicEvent("Mozart concert 2", "Mozart concert 2", 90, "Test2 music event", "Me", "Me", 0,
                        "My orchestra", "Me", "Song1, then song2"),
                new MusicEvent("Mozart concert 3", "Mozart concert 3", 90, "Test3 music event", "Me", "Me", 0,
                        "My orchestra", "Me", "Song1, then song2"));

        events = new ArrayList<>();
        events.addAll(theatres);
        events.addAll(dances);
        events.addAll(musics);

        performances = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            performances.add(new Performance(events.get(i), LocalDate.of(2019, 1, 1).atStartOfDay())); // Outdated
                                                                                                       // performance,
                                                                                                       // to test the
                                                                                                       // filter FUTURE
            performances.add(new Performance(events.get(i), LocalDate.of(2021, 1, 1).atStartOfDay()));
            performances.add(new Performance(events.get(i), LocalDate.of(2022, 1, 1).atStartOfDay()));
            performances.add(new Performance(events.get(i), LocalDate.of(2023, 1, 1).atStartOfDay()));
        }

        cycles = new ArrayList<>();
        // Create 3 cycles
        for (int i = 0; i < 3; i++) {
            cycles.add(new Cycle("Parts " + (i + 1) + " cycle", Set.of(musics.get(i), theatres.get(i), dances.get(i))));
            cycles.get(i).setDiscount(areaA, 0.2);
            cycles.get(i).setDiscount(areaB, 0.4);
            cycles.get(i).setDiscount(areaD, 0.1);
        }
    }

    /** Checks adding events */
    @Test
    public void testAddEvent() {
        for (Event e : events) {
            assertTrue(instance.addEvent(e));
            assertFalse(instance.addEvent(e)); // Cannot add the same event twice
        }
        assertEquals(Set.copyOf(events), Set.copyOf(instance.getEvents()));
    }

    /** Checks adding performances */
    @Test
    public void testAddPerformance() {
        for (Performance p : performances) {
            assertFalse(instance.addPerformance(p)); // Cannot add performances if there are no events
        }
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.getPerformances()));
        for (Event e : events) {
            instance.addEvent(e); // We add the events
        }
        for (Performance p : performances) {
            assertTrue(instance.addPerformance(p)); // Now we can add them
            assertFalse(instance.addPerformance(p)); // Cannot add them twice
        }
        assertEquals(Set.copyOf(performances), Set.copyOf(instance.getPerformances()));
    }

    /** Checks adding cycles */
    @Test
    public void testAddCycle() {
        for (Cycle c : cycles) {
            assertFalse(instance.addCycle(c)); // Cannot add cycles if the events are not in the app
        }
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.getCycles()));

        for (Event e : events) {
            instance.addEvent(e); // We add the events
        }
        for (Cycle c : cycles) {
            assertTrue(instance.addCycle(c)); // Now we can
            assertFalse(instance.addCycle(c)); // Cannot add cycles twice
        }
        assertEquals(Set.copyOf(cycles), Set.copyOf(instance.getCycles()));
    }

    /** Checks adding areas */
    @Test
    public void testAddArea() {
        assertTrue(instance.addArea(areaA, 120.0));
        assertFalse(instance.addArea(areaA, 120.0)); // Cannot add areas twice
        assertEquals(120.0, instance.getPassPrice(areaA), 0.0001); // We can also add composite areas
        assertTrue(instance.addArea(areaC)); // The price was also added correctly

        instance.addEvent(performances.get(0).getEvent());
        instance.addPerformance(performances.get(0)); // We schedule a performance
        assertFalse(instance.addArea(areaB, 200.0)); // Cannot add areas once there are performances scheduled
    }

    /** Checks ersing areas */
    @Test
    public void testRmArea() {
        instance.addArea(areaA, 120.0);
        assertTrue(instance.removeArea(areaA));
        assertFalse(instance.removeArea(areaB)); // areaB wasn't contained

        instance.addPerformance(performances.get(0)); // We schedule a performance
        assertFalse(instance.removeArea(areaA)); // Cannot remove areas once there are performances scheduled
    }

    /**
     * Searches events
     * 
     * @throws CreationException If any invalid parameters
     */
    @Test
    public void testSearchEvent() throws CreationException {
        addEverythingToTheApp();

        assertEquals(Set.copyOf(theatres), Set.copyOf(instance.searchEvent("Hamlet")));
        assertEquals(Set.copyOf(dances), Set.copyOf(instance.searchEvent("Ballet")));
        assertEquals(Set.copyOf(musics), Set.copyOf(instance.searchEvent("Mozart")));
        assertEquals(Set.of(theatres.get(0), musics.get(0), dances.get(0)), Set.copyOf(instance.searchEvent("1")));

        assertEquals(Set.copyOf(events), Set.copyOf(instance.searchEvent(" "))); // all the events
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchEvent("Lady Gaga"))); // no events
    }

    /**
     * Searches events
     * 
     * @throws CreationException If any invalid parameters
     */
    @Test
    public void testSearchPerformance() throws CreationException {
        addEverythingToTheApp();

        assertEquals(Set.copyOf(performances.subList(0, 12)), Set.copyOf(instance.searchPerformance("Hamlet")));
        assertEquals(Set.copyOf(performances.subList(12, 24)), Set.copyOf(instance.searchPerformance("Ballet")));
        assertEquals(Set.copyOf(performances.subList(24, 36)), Set.copyOf(instance.searchPerformance("Mozart")));

        assertEquals(Set.copyOf(performances), Set.copyOf(instance.searchPerformance(" "))); // all the performances
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchPerformance("Lady Gaga"))); // no performances

    }

    /**
     * Searches performances
     * 
     * @throws CreationException If any invalid parameters
     */
    @Test
    public void testSearchPerformanceWithFilters() throws CreationException {
        addEverythingToTheApp();

        assertEquals(Set.copyOf(performances.subList(0, 12)),
                Set.copyOf(instance.searchPerformance(" ", SearchFilter.THEATRE)));
        assertEquals(Set.copyOf(performances.subList(12, 24)),
                Set.copyOf(instance.searchPerformance(" ", SearchFilter.DANCE)));
        assertEquals(Set.copyOf(performances.subList(24, 36)),
                Set.copyOf(instance.searchPerformance(" ", SearchFilter.MUSIC)));

        Set<Performance> future = new HashSet<>();
        for (Performance p : performances) {
            if (p.getStatus() != PerformanceStatus.EXPIRED) {
                future.add(p);
            }
        }
        assertEquals(future, Set.copyOf(instance.searchPerformance(" ", SearchFilter.FUTURE)));

        // All empty
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchPerformance("Lady Gaga", SearchFilter.THEATRE)));
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchPerformance("Lady Gaga", SearchFilter.DANCE)));
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchPerformance("Lady Gaga", SearchFilter.MUSIC)));
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchPerformance("Lady Gaga", SearchFilter.FUTURE)));
    }

    /**
     * Searches for cycles
     * 
     * @throws CreationException If any invalid parameters
     */
    @Test
    public void testSearchCycle() throws CreationException {
        addEverythingToTheApp();

        for (int i = 0; i < 3; i++) {
            assertEquals(Set.of(cycles.get(i)), Set.copyOf(instance.searchCycle(Integer.toString(i + 1))));
            // We search the number (i+1)
        }
        assertEquals(Collections.EMPTY_SET, Set.copyOf(instance.searchCycle("Lady Gaga Cycle"))); // Empty search
        assertEquals(Set.copyOf(cycles), Set.copyOf(instance.searchCycle(" "))); // All the cycles
    }

    /**
     * Checks if a username is invalid
     * 
     * @throws InvalidUsernameException Expected this exception
     * 
     */
    @Test(expected = InvalidUsernameException.class)
    public void testCannotResgisterAsAdmin() throws InvalidUsernameException {
        instance.register("admin", "randomPassword");
    }

    /**
     * Checks if a username is taken
     * 
     * @throws InvalidUsernameException Expected this exception
     * 
     */
    @Test(expected = InvalidUsernameException.class)
    public void testUsernameIsUnique() throws InvalidUsernameException {
        assertEquals(new Customer("user1", "superSecurePassword"), instance.register("user1", "superSecurePassword"));
        instance.register("user1", "password2"); // cannot register twice
    }

    /**
     * Chekcs login
     * 
     * @throws InvalidLoginException    If username or password are wrong
     * @throws InvalidUsernameException If invalid username when registring
     */
    @Test
    public void testLoginSuccesful() throws InvalidLoginException, InvalidUsernameException {
        Customer c = instance.register("user1", "superSecurePassword");
        assertEquals(c, instance.login("user1", "superSecurePassword"));

        Manager m = new Manager("admin", "admin");
        assertEquals(m, instance.login("admin", "admin"));
    }

    /**
     * Chekcs if can login without registring
     * 
     * @throws InvalidLoginException    Expected this exception
     * @throws InvalidUsernameException If username is invalid when registring
     */
    @Test(expected = InvalidLoginException.class)
    public void testLoginFailed() throws InvalidLoginException, InvalidUsernameException {
        instance.login("user1", "superSecurePassword"); // login without register
    }

    /**
     * Checks for serializability
     * 
     * @throws IOException            IO error
     * @throws CreationException      Invalid parameters
     * @throws ClassNotFoundException Class not found
     */
    @Test
    public void testSerializable() throws IOException, CreationException, ClassNotFoundException {
        addEverythingToTheApp();
        TheatreTickets.saveTo("data/test.save");
        TheatreTickets.restore(); // restore the app to test if it can be read from the file

        // check that it has been restored
        assertEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getEvents()));
        assertEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getPerformances()));
        assertEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getCycles()));
        TheatreTickets.loadFrom("data/test.save");

        // check that the data has been read (assert that the sets are not null)
        assertNotEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getEvents()));
        assertNotEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getPerformances()));
        assertNotEquals(Collections.EMPTY_SET, Set.copyOf(TheatreTickets.getInstance().getCycles()));
    }

    /**
     * Incorrect loading
     * 
     * @throws IOException            Expected this exception
     * @throws ClassNotFoundException Class not found
     */
    @Test(expected = IOException.class)
    public void testIncorrectLoad() throws IOException, ClassNotFoundException {
        TheatreTickets.loadFrom("this/path/does/not/exist.save"); // wrong path
    }

    /**
     * Method to add all information to the app
     * 
     * @throws CreationException If any invalid argument
     */
    private void addEverythingToTheApp() throws CreationException {
        // add the events to the app
        for (Event e : events) {
            instance.addEvent(e);
        }
        // add performances
        for (Performance p : performances) {
            instance.addPerformance(p);
        }
        // add cycles
        for (Cycle c : cycles) {
            instance.addCycle(c);
        }
    }

    /** Restores TheatreTickets info */
    @After
    public void restore() {
        TheatreTickets.restore();
    }
}
