package controller.manager;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

import controller.shared.Controller;
import model.acts.events.Event;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import view.MainWindow;
import view.manager.editPerformance.EditPerformance;

/**
 * Controller handling/implementing edit perfomance multiple buttons
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EditPerformancesController extends Controller {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public EditPerformancesController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Goes back to the main page
     * 
     * @param ep  Current view
     * @param str String to display
     */
    private void goBack(EditPerformance ep, String str) {
        JOptionPane.showMessageDialog(ep,
                "Performance " + str.toLowerCase() + " succesfully. Customers will be notified.", str,
                JOptionPane.INFORMATION_MESSAGE);
        CardLayout cl = (CardLayout) view.getContentPane().getLayout();
        cl.show(view.getContentPane(), MainWindow.MenuName);
    }

    /**
     * Getter for postpone performance button
     * 
     * @return The action listener
     */
    public ActionListener getPostponeHandler() {
        return l -> {
            EditPerformance ep = view.getEditPerformancesView();
            Event e = instance.searchEvent(ep.getEventTitle()).get(0);
            Performance p = e.getPerformanceByDate(ep.getActualDate());
            p.postponePerformance(ep.getNewDateTime());
            goBack(ep, "Postponed");
        };
    }

    /**
     * Getter for cancel performance handler
     * 
     * @return The action listener
     */
    public ActionListener getCancelHandler() {
        return l -> {
            EditPerformance ep = view.getEditPerformancesView();
            Event e = instance.searchEvent(ep.getEventTitle()).get(0);
            Performance p = e.getPerformanceByDate(ep.getActualDate());
            p.cancelPerformance();
            goBack(ep, "Canceled");
        };
    }
}
