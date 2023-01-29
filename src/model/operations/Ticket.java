package model.operations;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import model.acts.events.Event;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import model.areas.*;
import model.areas.positions.*;
import model.operations.payments.*;
import es.uam.eps.padsof.telecard.*;
import es.uam.eps.padsof.tickets.*;

/**
 * Class that represents a ticket
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Ticket implements ITicketInfo, Serializable {
    /** Default UID */
    private static final long serialVersionUID = 1L;
    /** Position occupied */
    private Position position;
    /** payment method used */
    private PaymentMethod pm;
    /** operation associated */
    private Operation operation;
    /** id of the tickets */
    private int id;

    /**
     * Constructor
     * 
     * @param p  the associated position
     * @param op Operation to associate
     * @param pm payment method associated
     * @throws OrderRejectedException            Order was rejected
     * @throws FailedInternetConnectionException No internet connection
     * @throws InvalidCardNumberException        Card number is not valid
     */
    public Ticket(Position p, Operation op, PaymentMethod pm) throws OrderRejectedException {
        this.position = p;
        this.pm = pm;
        this.operation = op;
        this.id = TheatreTickets.getInstance().getLastTicketID();
        if (pm instanceof AnnualPass) {
            ((AnnualPass) pm).markAsBought(this.getPerformance());
        } else if (pm instanceof CyclePass) {
            ((CyclePass) pm).markAsBought(this.getPerformance());
        } else { // CreditCard
            ((CreditCard) pm).pay(this.getPrice(), "Bought ticket for the event \"" + this.getEventName() + "\"", true);
        }
        TheatreTickets.getInstance().incrementTicketID();
    }

    /**
     * Getter for the Date of the performance
     * 
     * @return the date
     */
    @Override
    public String getEventDate() {
        return this.getPerformance().getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    /**
     * Getter for the Event name
     * 
     * @return the event name
     */
    @Override
    public String getEventName() {
        return this.getEvent().getName();
    }

    /**
     * Getter for the id
     * 
     * @return the id of the ticket
     */
    @Override
    public int getIdTicket() {
        return this.id;
    }

    /**
     * Getter for the seat number, used in the generation of the pdf
     * 
     * @return the seat number
     */
    @Override
    public String getSeatNumber() {
        return this.getPosition().getTicketMsg();
    }

    /**
     * Getter for the name of the theatre
     * 
     * @return the name of the theatre
     */
    @Override
    public String getTheaterName() {
        return TheatreTickets.getInstance().getTheatreName();
    }

    /**
     * Getter for the position
     * 
     * @return the position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Getter for the operation
     * 
     * @return the operation
     */
    public Operation getOperation() {
        return this.operation;
    }

    /**
     * Getter for the payment Method
     * 
     * @return the payment method that was used to pay this ticket
     */
    public PaymentMethod getPaymentMethod() {
        return this.pm;
    }

    /**
     * Getter for the performancce
     * 
     * @return the performancce
     */
    public Performance getPerformance() {
        return this.getOperation().getPerformance();
    }

    /**
     * Getter for the event
     * 
     * @return the event
     */
    public Event getEvent() {
        return this.getPerformance().getEvent();
    }

    /**
     * Getter for the payment method
     * 
     * @return the payment method
     */
    public PaymentMethod getPayment() {
        return this.pm;
    }

    /**
     * Getter for the Area
     * 
     * @return the area
     */
    public NonComposite getArea() {
        return this.position.getArea();
    }

    /**
     * Getter for the price
     * 
     * @return the price
     */
    public double getPrice() {
        if (this.getPayment() instanceof Pass) {
            return 0;
        } else {
            return this.getEvent().getPrice(this.getArea());
        }
    }

    /**
     * Getter for the revenue made with this ticket
     * 
     * @return the revenue generated
     */
    public double getRevenue() {
        if (this.getPayment() instanceof Pass) {
            return ((Pass) this.getPayment()).getRevenue(this.operation.getPerformance());
        } else {
            return this.getPrice();
        }
    }

    /**
     * Method to print the ticket in a PDF file
     * 
     * @throws UnsupportedImageTypeException No image support
     * @throws NonExistentFileException      File does not exist
     */
    public void print() throws NonExistentFileException, UnsupportedImageTypeException {
        TicketSystem.createTicket(this, "printedTickets/");
    }
}
