package controller.shared;

import view.*;

import model.app.TheatreTickets;

/**
 * Generic controller containing basic information
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class Controller {
    /** Application instance */
    protected TheatreTickets instance;
    /** Main view containing all windows */
    protected MainWindow view;

    /**
     * Constructor
     * 
     * @param tt   The application
     * @param view Main JFrame window
     */
    public Controller(TheatreTickets tt, MainWindow view) {
        this.instance = tt;
        this.view = view;
    }
}
