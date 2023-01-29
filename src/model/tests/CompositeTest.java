package model.tests;

import model.areas.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester for {@link Composite}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CompositeTest {

    private Sitting areaA;
    private Standing areaB;
    private Standing areaC;
    private Composite mainArea;
    private Composite superior;

    /** Sets up variables */
    @Before
    public void setUp() {
        this.areaA = new Sitting("A", 5, 4);
        this.areaB = new Standing("B", 30);
        this.areaC = new Standing("C", 10);
        this.mainArea = new Composite("Main");
        this.superior = new Composite("Superior");
        this.superior.addArea(mainArea);
    }

    /** Test adding areas */
    @Test
    public void testAddArea() {
        mainArea.addAreas(areaA, areaB, areaC);
        mainArea.addArea(areaA); // Shouldnt be added since it is inside
        mainArea.addArea(mainArea); // Shouldnt be added since it is itself
        mainArea.addArea(superior); // Shouldnt be added since it contains the main area

        assertNotNull(mainArea.getAreas());
        assertTrue(mainArea.contains(areaA));
        assertTrue(mainArea.contains(areaB));
        assertTrue(mainArea.contains(areaC));
        assertFalse(mainArea.contains(mainArea));
        assertFalse(mainArea.contains(superior));

    }

    /** Deletes areas */
    @Test
    public void testRmArea() {
        mainArea.addAreas(areaA, areaB, areaC);
        mainArea.removeArea(areaA);
        assertFalse(mainArea.contains(areaA));
        assertTrue(mainArea.contains(areaB));
        assertTrue(mainArea.contains(areaC));

        mainArea.removeAreas(areaB, areaC);
        assertFalse(mainArea.contains(areaA));
        assertFalse(mainArea.contains(areaB));
        assertFalse(mainArea.contains(areaC));
        assertTrue(mainArea.getAreas().isEmpty());
    }

    /** Checks the capacity */
    @Test
    public void testGetCapacity() {
        mainArea.addAreas(areaA, areaB, areaC);
        assertEquals(60, mainArea.getCapacity());
    }

    /** Looks for cointainments */
    @Test
    public void testContains() {
        Composite areaD = new Composite("D");
        areaD.addArea(areaC);
        mainArea.addAreas(areaA, areaB, areaD);
        mainArea.addArea(mainArea);
        mainArea.addArea(superior);

        assertTrue(mainArea.contains(areaA));
        assertTrue(mainArea.contains(areaB));
        assertTrue(mainArea.contains(areaC)); // Should be contained as it is inside areaD which is inside the mainArea
        assertTrue(mainArea.contains(areaD));
        assertFalse(mainArea.contains(mainArea)); // Shouldnt be contained since it is itself
        assertFalse(mainArea.contains(superior)); // Shouldnt be contained since it contains the main area
    }
}
