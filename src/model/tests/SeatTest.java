package model. tests;

import java.time.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model. areas.*;
import model. areas.positions.*;

/**
 * Tester for {@link Seat}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SeatTest {

    private Sitting sittingArea;
    private Seat seat;

    /** Sets up a seat */
    @Before
    public void setUp() {
        this.sittingArea = new Sitting("Test", 5, 5);
        this.seat = new Seat(1, 1, sittingArea);
    }

    /** Sets and gets rows and colums */
    @Test
    public void testGetAndSetRowCol() {
        this.seat.setRow(3);
        this.seat.setColumn(4);
        assertEquals(3, this.seat.getRow());
        assertEquals(4, this.seat.getColumn());
    }

    /** Disables seats */
    @Test
    public void testDisable() {
        LocalDateTime today = LocalDateTime.now();

        assertFalse(this.seat.isDisabled(today));

        // check if the seat disables correctly
        this.seat.disable(today.minusDays(2), today.plusDays(2));
        assertFalse(this.seat.isDisabled(today.minusDays(3)));
        assertFalse(this.seat.isDisabled(today.plusDays(3)));
        assertTrue(this.seat.isDisabled(today));
    }
}
