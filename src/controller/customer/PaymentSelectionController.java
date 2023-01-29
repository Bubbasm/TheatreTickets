package controller.customer;

import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import view.*;
import view.customer.purchase.AnnualPassPurchase;
import view.customer.purchase.CyclePassPurchase;
import view.customer.purchase.PaymentSelection;
import view.customer.search.Search;
import model.app.TheatreTickets;
import model.users.*;
import model.operations.*;
import model.acts.cycles.*;
import model.acts.events.Event;
import model.acts.performances.Performance;
import model.areas.*;
import model.areas.positions.*;
import model.exceptions.*;
import model.operations.payments.*;
import java.util.List;

/**
 * Controller managing all payments
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PaymentSelectionController extends Controller {
    /** Main controller */
    private MainController mc;
    /** Seats selected if neccesary */
    private Set<Seat> seats;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller
     */
    public PaymentSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Setter for seats to buy
     * 
     * @param seats Set of seats
     */
    public void setSeatsPurchase(Set<Seat> seats) {
        this.seats = seats;
    }

    /**
     * Getter for action when requesting to purchase annual pass
     * 
     * @return The action listener
     */
    public ActionListener getBuyAnnualPassButtonHandler() {
        return e -> {
            AnnualPassPurchase app = new AnnualPassPurchase(mc.getTreePanelController().createTree(true));

            view.updateAnnualPassPurchaseView(app);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AnnualPassPurchaseName);
        };
    }

    /**
     * Getter for action when requesting to purchase cycle pass
     * 
     * @return The action listener
     */
    public ActionListener getBuyCyclePassButtonHandler() {
        return a -> {
            PaymentSelection ps = view.getPaymentSelectionView();
            if (instance.getCycles().isEmpty()) {
                JOptionPane.showMessageDialog(ps, "There are no cycles", "Cannot buy cycle passes",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Map<String, Cycle> cycles = new HashMap<>();
            Map<String, String> map = new HashMap<>();
            for (Cycle c : instance.getCycles()) {
                String events = "";
                for (Event e : c.getEvents()) {
                    events += "- " + e.getTitle() + "\n";
                }
                cycles.put(c.getName(), c);
                map.put(c.getName(), events);
            }

            // create area selection
            Cycle c = cycles.values().iterator().next();
            CyclePassPurchase cpp = new CyclePassPurchase(mc.getTreePanelController().createTree(c), map);
            cpp.setCycleSelectorHandler(e -> {
                Cycle select = cycles.get(cpp.getCycleSelected());
                cpp.updateTree(mc.getTreePanelController().createTree(select));
            });

            view.updateCyclePassPurchaseView(cpp);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.CyclePassPurchaseName);
        };
    }

    /**
     * Getter for action of a regular purchase with sitting area. Note: the set of
     * seats must be set before the corresponding view is established. Additionally,
     * performance and non composite sitting area is requested to be established
     * 
     * @return The action listener
     */
    public ActionListener getRegularSeatingHandler() {
        return e -> {
            Set<Seat> seat = seats;
            PaymentSelection ps = view.getPaymentSelectionView();
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            Performance perf = mc.getPerformance();
            Sitting sit = (Sitting) mc.getNonComposite();

            Set<PaymentMethod> payments = new HashSet<>();

            int annual = ps.getTicketsAnnualPass();
            if (annual > 0) {
                List<AnnualPass> passes = new ArrayList<>((c.getAnnualPasses(perf, sit)));
                payments.addAll(passes.subList(0, annual));
            }

            int cycle = ps.getTicketsCyclePass();
            if (cycle > 0) {
                List<CyclePass> passes = new ArrayList<>((c.getCyclePasses(perf, sit)));
                payments.addAll(passes.subList(0, cycle));
            }
            try {
                if (ps.getTicketsCreditCard() > 0)
                    payments.add(new CreditCard(ps.getCreditCardNumber()));
                System.out.println("Number of paymeths: " + payments.size());
                if (c.buyTickets(perf, sit, payments, seat) == false) {
                    JOptionPane.showMessageDialog(ps, "Error in purchase", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (CreationException ce) {
                JOptionPane.showMessageDialog(ps, "Invalid credit card number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            seats = null;
            succesfulPurchase(ps);
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.SearchRegisteredName);
        };
    }

    /**
     * Getter for action of a regular purchase with standing area. Note: Performance
     * and non composite sitting area must be established before this action can get
     * performed
     * 
     * @return The action listener
     */
    public ActionListener getRegularStandingHandler() {
        return e -> {
            PaymentSelection ps = view.getPaymentSelectionView();
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            Performance perf = mc.getPerformance();
            Standing stan = (Standing) mc.getNonComposite();

            Set<PaymentMethod> payments = new HashSet<>();
            int credit = ps.getTicketsCreditCard();
            int annual = ps.getTicketsAnnualPass();
            if (annual > 0) {
                List<AnnualPass> passes = new ArrayList<>((c.getAnnualPasses(perf, stan)));
                payments.addAll(passes.subList(0, annual));
            }

            int cycle = ps.getTicketsCyclePass();
            if (cycle > 0) {
                List<CyclePass> passes = new ArrayList<>((c.getCyclePasses(perf, stan)));
                payments.addAll(passes.subList(0, cycle));
            }
            try {
                if (credit > 0)
                    payments.add(new CreditCard(ps.getCreditCardNumber()));
                System.out.println("Number of paymeths: " + payments.size());
                if (c.buyTickets(perf, stan, credit + annual + cycle, payments) == false) {
                    JOptionPane.showMessageDialog(ps, "Error in purchase", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (CreationException ce) {
                JOptionPane.showMessageDialog(ps, "Invalid credit card number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            succesfulPurchase(ps);
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.SearchRegisteredName);
        };
    }

    /**
     * Getter for action of a reservation. Note: The performance and non composite
     * area must be established before and reservation id set to the action command
     * of button
     * 
     * @return The action listener
     */
    public ActionListener getReservationHandler() {
        return e -> {
            PaymentSelection ps = view.getPaymentSelectionView();
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            Reservation res = c.getReservation(Integer.parseInt(e.getActionCommand()));
            Performance perf = mc.getPerformance();
            NonComposite area = mc.getNonComposite();

            Set<PaymentMethod> payments = new HashSet<>();

            int annual = ps.getTicketsAnnualPass();
            if (annual > 0) {
                List<AnnualPass> passes = new ArrayList<>(c.getAnnualPasses(perf, area));
                payments.addAll(passes.subList(0, annual));
            }

            int cycle = ps.getTicketsCyclePass();
            if (cycle > 0) {
                List<CyclePass> passes = new ArrayList<>(c.getCyclePasses(perf, area));
                payments.addAll(passes.subList(0, cycle));
            }

            try {
                if (ps.getTicketsCreditCard() > 0)
                    payments.add(new CreditCard(ps.getCreditCardNumber()));
                System.out.println("Number of paymeths: " + payments.size());
                if (c.confirmReservation(payments, res) == false) {
                    JOptionPane.showMessageDialog(ps, "Error in reservation", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (CreationException ce) {
                JOptionPane.showMessageDialog(ps, "Invalid credit card number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            succesfulPurchase(ps);
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.SearchRegisteredName);
        };
    }

    /**
     * Displays message of succesful purchase
     * 
     * @param pan Panel to display in
     */
    private void succesfulPurchase(JPanel pan) {
        Search search = view.getSearchRegisteredView();
        search.emptySearch();
        search.clearSearch();
        JOptionPane.showMessageDialog(pan,
                "Your payment has been completed successfully. You can find your tickets at printedTickets directory",
                "Purchase succesful", JOptionPane.INFORMATION_MESSAGE);

    }
}
