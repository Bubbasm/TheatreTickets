package controller.customer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.MainController;
import view.*;
import view.customer.purchase.*;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import model.users.Customer;
import model.areas.*;

import java.time.format.*;

/**
 * Controller for standing position selection
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class StandingPositionsSelectionController extends SelectionController {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public StandingPositionsSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
    }

    /**
     * Implements actions performed when requesting specified number of positions. Note: area and
     * performance must be set before
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        StandingPositionsSelection sps = view.getStandingPositionsSelectionView();
        Performance perf = mc.getPerformance();
        int numTicks = sps.getRequestedPositions();
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();

        // We know it is a standing area as StandingPositionsSelection is shown
        if (view.getAreaSelectionView().isToBeReserved()) {
            if (c.reserveTickets(perf, (Standing) mc.getNonComposite(), numTicks) == false) {
                JOptionPane.showMessageDialog(sps, "Cannot reserve these positions", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                succesfulReservation(numTicks, sps);
            }
        } else {
            goToPay(numTicks);
        }
    }

    /**
     * Goes to payment checkout
     * 
     * @param numTicks Number of positions to purhcase
     */
    protected void goToPay(int numTicks) {
        NonComposite area = mc.getNonComposite();
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        Performance perf = mc.getPerformance();
        PaymentSelection ps = new PaymentSelection(area.getName(), perf.getEvent().getTitle(),
                perf.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), numTicks,
                perf.getEvent().getPrice(area), c.getAnnualPasses(perf, area).size(),
                c.getCyclePasses(perf, area).size());

        view.updatePaymentSelectionView(ps);
        ActionListener finishPurchase = mc.getPaymentSelectionController().getRegularStandingHandler();
        ps.setContinueButtonHandler(finishPurchase);

        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);
    }
}
