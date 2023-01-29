package controller.manager;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.swing.JOptionPane;

import controller.shared.Controller;
import model.acts.events.Event;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import view.MainWindow;
import view.manager.addPerformance.AddPerformance;

/**
 * Class handling adding performance controller. As only one action event is
 * considered, it is decided to implement ActionListener
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddPerformanceController extends Controller implements ActionListener {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public AddPerformanceController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Implementation of add performance button in AddPerformance
     * 
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        AddPerformance ap = view.getAddPerformanceView();
        Map<String, Event> map = new HashMap<>();
        for (Event e : instance.getEvents()) {
            map.put(e.getTitle(), e);
        }

        try {
            Performance p = new Performance(map.get(ap.getEvent()), ap.getDateTime());
            instance.addPerformance(p);


            JOptionPane.showMessageDialog(ap, "Performance added succesfully", "Performance added",
                    JOptionPane.INFORMATION_MESSAGE);

            ap.clearFields();
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.MenuName);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(ap, "Incorrect format for date/time", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
