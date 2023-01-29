package controller.customer;

import view.*;
import view.customer.search.*;
import model.acts.performances.*;
import model.app.TheatreTickets;
import model.enums.PerformanceStatus;

import java.awt.*;
import java.awt.event.*;
import java.time.format.*;

import controller.MainController;

/**
 * Specific search controller for unregistered users. Bottom layer buttons are
 * hidden and no purchase can be made
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SearchUnregisteredController extends SearchController {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public SearchUnregisteredController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
        panel = view.getSearchUnregisteredView();
    }

    /**
     * Getter for the performance entry to be generated.
     *
     * @param p    Performance to add
     * @param bool If event details must be shown
     * @param uid  Unique id for entry, to keep track of button pressed
     * @return The performance entry
     */
    @Override
    protected PerformanceEntry getPerformanceEntry(Performance p, boolean bool, Integer uid) {
        PerformanceStatus ps = p.getStatus();
        PerformanceEntry pe = new PerformanceEntry(p.getEvent().getTitle(),
                p.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), p.isSoldOut(),
                ps.equals(PerformanceStatus.CANCELLED), bool);

        pe.hideButton();
        strPerformance.put(uid.toString(), p);

        return pe;
    }

    /**
     * Getter for log in now button action
     *
     * @return The action listener
     */
    public ActionListener getLogInNowHandler() {
        return e -> {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.LogInSignUpName);
        };
    }
}
