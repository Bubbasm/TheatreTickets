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
import model.exceptions.CreationException;
import model.operations.payments.CreditCard;
import model.users.Customer;

/**
 * Controller for purchasing an annual pass
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AnnualPassPurchaseController extends Controller implements ActionListener {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public AnnualPassPurchaseController(TheatreTickets tt, MainWindow view, MainController mc) {
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
     * Implementation of the main button (buy pass)
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        AnnualPassPurchase app = view.getAnnualPassPurchaseView();
        NonComposite selected = instance.searchNonCompositeArea(app.getSelection());
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        if (selected == null) {
            JOptionPane.showMessageDialog(app, "Invalid area", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            CreditCard cc = new CreditCard(app.getCreditCardNumber());
            if (c.purchaseAnnualPass(selected, cc) == false) {
                JOptionPane.showMessageDialog(app,
                        "Operation rejected. Please contact with your credit card manager and try again later", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selected.equals(mc.getNonComposite())) {
                PaymentSelection ps = view.getPaymentSelectionView();
                ps.incrementAnnualPassMaximum();
            }
        } catch (CreationException ce) {
            JOptionPane.showMessageDialog(app, "Invalid credit card number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(app, "The annual pass is now available to use", "Purchase succesful",
                JOptionPane.INFORMATION_MESSAGE);
        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.PaymentSelectionName);

    }
}
