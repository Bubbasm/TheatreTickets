package model. tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import model. areas.*;
import model. acts.performances.organization.*;

/**
 * Tester for {@link PerformanceStanding}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceStandingTest {

    /** Standing area to test */
    private Standing area;
    /** Object to be tested */
    private PerformanceStanding occupancy;

    /**
     * We initialize attributes
     */
    @Before
    public void setUp() {
        area = new Standing("Test area", 5);
        occupancy = new PerformanceStanding(area, 4);
    }

    /**
     * Occupies all positions to see if more positions can be occupied. It also
     * checks if that area has been sold out
     */
    @Test
    public void occupyNPositions() {
        assertEquals(4, occupancy.getAvailablePositons());
        assertFalse(occupancy.occupy(List.copyOf(Collections.nCopies(5, area.getAPosition()))));
        assertTrue(occupancy.occupy(List.copyOf(Collections.nCopies(4, area.getAPosition()))));
        assertTrue(occupancy.isSoldOut());
        assertFalse(occupancy.occupy(List.copyOf(Collections.nCopies(4, area.getAPosition()))));
        assertTrue(occupancy.unoccupy(area.getAPosition()));
        assertFalse(occupancy.isSoldOut());

        assertEquals(3, occupancy.getOccupiedPossitions());
        assertEquals(1, occupancy.getAvailablePositons());
    }

    /**
     * Unoccupies certain position and tries to unoccupy unknown occupied positions
     */
    @Test
    public void unoccupyNPositions() {
        assertTrue(occupancy.occupy(area.getAPosition()));
        assertFalse(occupancy.unoccupy(Collections.nCopies(2, area.getAPosition())));
        assertTrue(occupancy.unoccupy(area.getAPosition()));
        assertFalse(occupancy.unoccupy(area.getAPosition()));
    }

    /**
     * Checks availability
     */
    @Test
    public void availabilityPositions() {
        assertTrue(occupancy.occupy(Collections.nCopies(4, area.getAPosition())));
        assertTrue(occupancy.isOccupied(area.getAPosition()));
        assertTrue(occupancy.isSoldOut());
        assertTrue(occupancy.unoccupy(Collections.nCopies(1, area.getAPosition())));
        assertFalse(occupancy.isSoldOut());
    }
}
