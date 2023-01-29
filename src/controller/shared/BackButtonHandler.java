package controller.shared;

import java.awt.event.*;
import java.awt.*;

import view.*;
import view.customer.search.*;

import model.app.TheatreTickets;
import model.users.Manager;
import model.users.UnregisteredUser;

/**
 * Action listener that performs a highly demanded action, returning to the main
 * page
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class BackButtonHandler extends Controller implements ActionListener {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public BackButtonHandler(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Implemetation for returning back to homepage
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        Search search;
        String name;

        if (TheatreTickets.getCurrentCustomer() instanceof Manager) {
            name = MainWindow.MenuName;
        } else {
            if (TheatreTickets.getCurrentCustomer() instanceof UnregisteredUser) {
                search = view.getSearchUnregisteredView();
                name = MainWindow.SearchUnregisteredName;
            } else {
                search = view.getSearchRegisteredView();
                name = MainWindow.SearchRegisteredName;
            }
            search.clearSearch();
            search.emptySearch();
        }

        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), name);

    }

}
