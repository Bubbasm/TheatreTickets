package model. tests;

import org.junit.Test;

import model. exceptions.CreationException;
import model. operations.payments.*;

/**
 * Tester for {@link CreditCard}
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CreditCardTest {
    /**
     * Checks creation of a credit card
     * 
     * @throws CreationException If any card number is invalid
     */
    @Test(expected = CreationException.class)
    public void testCreation() throws CreationException {
        new CreditCard("10"); // Should throw an exception
    }

    // Testing pay/deposit doesnt make sense since it is done through the API
}
