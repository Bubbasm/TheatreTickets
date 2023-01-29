package controller.customer;

import view.*;
import view.customer.purchase.PaymentSelection;
import view.customer.reservations.ReservationCenter;
import view.customer.reservations.ReservationEntry;

import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;

import java.awt.*;
import java.awt.event.*;

import java.time.format.*;

import model.users.*;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import model.areas.NonComposite;
import model.operations.Reservation;

/**
 * Reservation entries controller
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ReservationCenterController extends Controller {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public ReservationCenterController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Getter for confimation button action
     *
     * @param res Reservation to handle
     * @return The action listener
     */
    public ActionListener getConfirmReservation(Reservation res) {
        return a -> {
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            NonComposite area = res.getArea();
            Performance perf = res.getPerformance();
            String title = perf.getEvent().getName();
            String date = perf.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            int numOfTickets = res.getNumberOfTickets();
            double price = res.getPricePerTicket();
            int maxAnnual = c.getAnnualPasses(perf, res.getArea()).size();
            int maxCycle = c.getCyclePasses(perf, res.getArea()).size();
            PaymentSelection ps = new PaymentSelection(area.getName(), title, date, numOfTickets, price, maxAnnual,
                    maxCycle);
            mc.setPerformance(perf);
            mc.setNonComposite(area);

            view.updatePaymentSelectionView(ps);
            PaymentSelectionController psc = mc.getPaymentSelectionController();

            ActionListener finishPurchase = psc.getReservationHandler();
            ps.setContinueButtonActionCommand(Integer.valueOf(res.getId()).toString());
            ps.setContinueButtonHandler(finishPurchase);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);
        };
    }

    /**
     * Getter for confimation button action
     *
     * @param res Reservation to handle
     * @param re Reservation entry
     * @return The action listener
     */
    public ActionListener getCancelReservation(Reservation res, ReservationEntry re) {
        return a -> {
            ReservationCenter rc = view.getReservationCenterView();
            int n = JOptionPane.showConfirmDialog(rc,
                    "Are you sure you want to cancel the reservation? This operation cannot be reversed", "",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (n == JOptionPane.YES_OPTION) {
                res.cancel();
                re.updateCancel();
            } else {
            }
        };
    }
}
