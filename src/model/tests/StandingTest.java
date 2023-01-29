package model. tests;

import model. areas.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tester for {@link Standing}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class StandingTest {
    /** Checks the capacity of the area */
    @Test
    public void testGetCapacity() {
        Standing area = new Standing("test", 400);

        int result = area.getCapacity();
        assertEquals(400, result);

        area.setCapacity(100);
        result = area.getCapacity();
        assertEquals(100, result);
    }
}
