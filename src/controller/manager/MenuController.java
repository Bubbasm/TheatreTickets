package controller.manager;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import view.manager.addCycle.AddCycleFirst;
import view.manager.disableSeats.DisableSeatsFirst;
import view.manager.menu.Menu;
import view.manager.modifyTheatre.ModifyTheatreFirst;
import view.manager.stats.Statistics;
import model.acts.performances.*;

import java.util.*;
import java.util.List;

import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;
import model.app.TheatreTickets;
import model.enums.PerformanceStatus;
import model.acts.events.Event;
import view.MainWindow;

/**
 * Controller for all buttons for theatre managing
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class MenuController extends Controller {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller
     */
    public MenuController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getAddEventHandler() {
        return a -> {
            Menu m = view.getMenuView();
            if (instance.getMainArea().getNonCompositeWithin().isEmpty()) {
                JOptionPane.showMessageDialog(m,
                        "Cannot add events because the theatre is not configured.\n Please add Non-Composite areas to the theatre",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.AddEventFirstName);
            }
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getAddPerformanceHandler() {
        return a -> {
            Menu m = view.getMenuView();
            List<String> events = new LinkedList<>();
            for (Event e : instance.getEvents()) {
                events.add(e.getTitle());
            }
            if (events.isEmpty()) {
                JOptionPane.showMessageDialog(m, "Cannot add performances because there are no events", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                view.getAddPerformanceView().update(events);
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.AddPerformanceName);
            }
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getEditPerformanceHandler() {
        return a -> {
            Menu m = view.getMenuView();
            List<String> perfs = new LinkedList<>();
            for (Performance p : instance.getPerformances()) {
                if (p.getStatus() != PerformanceStatus.CANCELLED && p.getStatus() != PerformanceStatus.EXPIRED) {
                    perfs.add(p.getEvent().getTitle() + " at "
                            + p.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                }
            }
            if (perfs.isEmpty()) {
                JOptionPane.showMessageDialog(m,
                        "Cannot edit performances because there are no performances proggramed", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                view.getEditPerformancesView().update(perfs);
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.EditPerformanceName);
            }
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getAddCycleHandler() {
        return a -> {
            Menu m = view.getMenuView();
            List<String> events = new LinkedList<>();
            for (Event e : instance.getEvents()) {
                events.add(e.getTitle());
            }
            if (events.isEmpty()) {
                JOptionPane.showMessageDialog(m, "Cannot add cycles because there are no events", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                view.updateAddCycleFirstView(new AddCycleFirst(events));
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.AddCycleFirstName);
            }
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getDisableSeatsHandler() {
        return a -> {
            view.updateDisableSeatsFirstView(new DisableSeatsFirst(mc.getTreePanelController().createTree()));
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.DisableSeatsFirstName);
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getAdditionalSettingsHandler() {
        return a -> {
            view.getAdditionalSettingsView().update(instance.getMaxTicketsPerPurchase(),
                    instance.getMaxTicketsPerReservation(), instance.getHoursForReservation());

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AdditionalSettingsName);
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getStatisticsHandler() {
        return a -> {
            Menu m = view.getMenuView();
            if (instance.getPerformances().isEmpty()) {
                JOptionPane.showMessageDialog(m,
                        "Cannot calculate any statistics as there are no performances programmed", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<String> events = new LinkedList<>();
            Map<String, List<String>> perfMap = new HashMap<>();
            for (Event e : instance.getEvents()) {
                events.add(e.getTitle());
                List<String> perf = new LinkedList<>();
                for (Performance p : e.getPerformances()) {
                    perf.add(p.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                }
                perfMap.put(e.getTitle(), perf);
            }
            view.updateStatisticsView(new Statistics(events, perfMap));
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.StatisticsName);
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getModifyTheatreHandler() {
        return a -> {
            Menu m = view.getMenuView();
            if (!instance.isEditable()) {
                JOptionPane.showMessageDialog(m,
                        "Cannot edit the theatre because there are events/performances/cycles already programmed",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                view.updateModifyTheatreFirstView(
                        new ModifyTheatreFirst(mc.getTreePanelController().createTree("Theatre")));
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.ModifyTheatreFirstName);
            }
        };
    }

    /**
     * Getter for the button
     * 
     * @return The action listener
     */
    public ActionListener getLogOutHandler() {
        return a -> {
            instance.logout();
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.LogInSignUpName);
        };
    }
}
