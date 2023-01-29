package model. operations.payments;

import es.uam.eps.padsof.telecard.*;
import model. exceptions.CreationException;

/**
 * Class for Credit card payment method
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CreditCard extends PaymentMethod {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Credir card number */
    private String number;

    /**
     * Constructor
     * 
     * @param number credit card number
     * @throws CreationException if any invalid parameter
     */
    public CreditCard(String number) throws CreationException {
        if (TeleChargeAndPaySystem.isValidCardNumber(number)) {
            this.number = number;
        } else {
            throw new CreationException("Invalid Credit Card Number: " + this.number);
        }
    }

    /**
     * Pay some quantity with this credit card
     * 
     * @param qty     quantity to pay
     * @param subject String that explains the purchase
     * @param trace   if output is desired
     * @throws InvalidCardNumberException        Invalid card number
     * @throws FailedInternetConnectionException No internet connection
     * @throws OrderRejectedException            Order was rejected
     */
    public void pay(Double qty, String subject, boolean trace)
            throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException {
        if (qty < 0)
            throw new OrderRejectedException("Cannot pay a negative ammount", this.number);
        TeleChargeAndPaySystem.charge(this.number, subject, -qty, trace);
    }

    /**
     * Deposit some quantity with this credit card
     * 
     * @param qty     quantity to deposit
     * @param subject String that explains the deposit
     * @param trace   If output is desired
     * @throws InvalidCardNumberException        Invalid card number
     * @throws FailedInternetConnectionException No internet connection
     * @throws OrderRejectedException            Order was rejected
     */
    public void deposit(Double qty, String subject, boolean trace)
            throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException {
        if (qty < 0)
            throw new OrderRejectedException("Cannot deposit a negative ammount", this.number);
        TeleChargeAndPaySystem.charge(this.number, subject, qty, trace);
    }
}
