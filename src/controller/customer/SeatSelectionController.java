package controller.customer;

import java.awt.*;
import java.awt.event.*;

import view.*;
import view.customer.purchase.*;

import model.app.*;
import model.areas.positions.*;
import model.areas.NonComposite;
import model.users.Customer;
import model.acts.performances.*;

import java.util.*;

import controller.MainController;

import java.time.format.*;

/**
 * Controller for seat selection
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class SeatSelectionController extends SelectionController {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public SeatSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
    }

    /**
     * Goes to payment checkout
     * 
     * @param seats Seats to purhcase
     */
    protected void goToPay(Set<Seat> seats) {
        NonComposite area = mc.getNonComposite();
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        Performance perf = mc.getPerformance();
        PaymentSelection ps = new PaymentSelection(area.getName(), perf.getEvent().getTitle(),
                perf.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), seats.size(),
                perf.getEvent().getPrice(area), c.getAnnualPasses(perf, area).size(),
                c.getCyclePasses(perf, area).size());

        view.updatePaymentSelectionView(ps);
        PaymentSelectionController psc = mc.getPaymentSelectionController();
        psc.setSeatsPurchase(seats);
        ActionListener finishPurchase = psc.getRegularSeatingHandler();
        ps.setContinueButtonHandler(finishPurchase);

        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);
    }
}
