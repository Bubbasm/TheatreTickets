package controller.customer;

import java.awt.event.*;

import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;

import java.awt.*;
import view.*;
import view.customer.purchase.*;
import model.app.TheatreTickets;
import model.areas.NonComposite;
import model.acts.cycles.*;
import model.exceptions.CreationException;
import model.operations.payments.CreditCard;
import model.users.Customer;

/**
 * Controller to purchase a cycle pass
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CyclePassPurchaseController extends Controller implements ActionListener {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller
     */
    public CyclePassPurchaseController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Getter for back button
     * 
     * @return The action listener
     */
    public ActionListener getBackButtonHandler() {
        return a -> {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);
        };
    }

    /**
     * Implementation of purchasing cycle pass
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        CyclePassPurchase cpp = view.getCyclePassPurchaseView();
        NonComposite selected = instance.searchNonCompositeArea(cpp.getAreaSelected());
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        Cycle cyc = instance.searchCycleByName(cpp.getCycleSelected());
        if (selected == null) {
            JOptionPane.showMessageDialog(cpp, "Invalid area", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cyc == null) {
            JOptionPane.showMessageDialog(cpp, "Invalid cycle", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            CreditCard cc = new CreditCard(cpp.getCreditCardNumber());
            if (c.purchaseCyclePass(cyc, selected, cc) == false) {
                JOptionPane.showMessageDialog(cpp,
                        "Operation rejected. Please contact with your credit card manager and try again later", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean aux = cyc.getEvents().contains(mc.getPerformance().getEvent());
            aux &= selected.equals(mc.getNonComposite());
            if (aux) {
                PaymentSelection ps = view.getPaymentSelectionView();
                ps.incrementCyclePassMaximum();
            }
        } catch (CreationException ce) {
            JOptionPane.showMessageDialog(cpp, "Invalid credit card number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(cpp, "The cycle pass is now available to use", "Purchase succesful",
                JOptionPane.INFORMATION_MESSAGE);
        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);
    }
}
