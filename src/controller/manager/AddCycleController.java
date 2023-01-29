package controller.manager;

import model.acts.cycles.Cycle;
import model.acts.events.Event;
import model.app.TheatreTickets;
import model.areas.NonComposite;
import model.areas.Sitting;
import view.MainWindow;
import view.manager.addCycle.AddCycleFirst;
import view.manager.addCycle.AddCycleSecond;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.JOptionPane;

import controller.shared.Controller;

/**
 * Controller for adding cycles
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddCycleController extends Controller {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public AddCycleController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Getter for proccesing step one of adding a cycle
     * 
     * @return The action listener
     */
    public ActionListener getAddCycleFirstHandler() {
        return a -> {
            AddCycleFirst acf = view.getAddCycleFirstView();
            if(acf.getCycleName().equals("")){
                JOptionPane.showMessageDialog(acf,
                        "The cycle name cannot be empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if (acf.getEvents().isEmpty()) {
                JOptionPane.showMessageDialog(acf,
                        "A cycle must contain an event.\nPlease select some events before continuing", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<String> areaList = new LinkedList<>();
            for (NonComposite nc : instance.getMainArea().getNonCompositeWithin()) {
                areaList.add(nc.getName() + " " + (nc instanceof Sitting ? "(Sitting)" : "(Standing)"));
            }
            view.updateAddCycleSecondView(new AddCycleSecond(areaList));
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AddCycleSecondName);
        };
    }

    /**
     * Getter for finishing adding cycle
     * 
     * @return The action listener
     */
    public ActionListener getAddCycleSecondHandler() {
        return a -> {
            AddCycleFirst acf = view.getAddCycleFirstView();
            AddCycleSecond acs = view.getAddCycleSecondView();

            Map<String, Double> discounts;
            try {
                discounts = acs.getDiscounts();
                Set<Event> events = new HashSet<>();
                for (String s : acf.getEvents()) {
                    events.add(instance.searchEventByName(s));
                }
                Cycle c = new Cycle(acf.getCycleName(), events);

                for (String area : discounts.keySet()) {
                    c.setDiscount(instance.searchNonCompositeArea(area), discounts.get(area));
                }

                instance.addCycle(c);
                JOptionPane.showMessageDialog(acs, "Cycle added successfuly", "Cycle added",
                        JOptionPane.INFORMATION_MESSAGE);
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.MenuName);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(acs, "Incorrect price format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    /**
     * Getter for back button, to not add the cycle
     * 
     * @return The action listener
     */
    public ActionListener getBackButtonHandler() {
        return a -> {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AddCycleFirstName);
        };
    }
}