package controller.customer;

import java.awt.*;
import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;

import java.awt.event.*;

import view.*;
import view.customer.search.*;
import model.app.*;

/**
 * Controller for arbitrary position selection
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class SelectionController extends Controller implements ActionListener {
    /** Main controller */
    protected MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public SelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Displays message and goes back to main menu
     * @param numTicks Number of tickets that have been reserved
     * @param pan      Panel to show information in
     */
    protected void succesfulReservation(int numTicks, JPanel pan) {
        Search search = view.getSearchRegisteredView();
        search.emptySearch();
        search.clearSearch();
        JOptionPane.showMessageDialog(pan,
                "You have reserved " + numTicks + " tickets. You can find your reservations from the main menu",
                "Succesful reservation", JOptionPane.INFORMATION_MESSAGE);

        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.SearchRegisteredName);
    }

}
