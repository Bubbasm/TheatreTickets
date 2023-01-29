package model. tests;

import model. areas.*;
import java.time.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester for {@link Sitting}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SittingTest {
    private Sitting area;
    private Sitting areaB;
    private Sitting areaC;

    /**
     * Sets up the parameters
     */
    @Before
    public void setUp() {
        this.area = new Sitting("test", 30, 10);
        this.areaB = new Sitting("test2", 20, 5);
        this.areaC = new Sitting("test3", 1, 1);
    }

    /** Tests the capacity */
    @Test
    public void testGetCapacity() {
        assertEquals(300, this.area.getCapacity());
        assertEquals(100, this.areaB.getCapacity());
        assertEquals(1, this.areaC.getCapacity());
    }

    /** Tests the disabling */
    @Test
    public void testDisable() {
        LocalDateTime today = LocalDateTime.now();

        // check if the seat disables correctly
        this.area.disable(1, 1, today.minusDays(2), today.plusDays(2));
        assertFalse(this.area.getSeat(1, 1).isDisabled(today.minusDays(3)));
        assertFalse(this.area.getSeat(1, 1).isDisabled(today.plusDays(3)));
        assertTrue(this.area.getSeat(1, 1).isDisabled(today));
    }
}
