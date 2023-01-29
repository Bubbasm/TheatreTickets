package controller.manager;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.JOptionPane;

import controller.shared.Controller;
import model.acts.events.DanceEvent;
import model.acts.events.Event;
import model.acts.events.MusicEvent;
import model.acts.events.TheatreEvent;
import model.app.TheatreTickets;
import model.areas.NonComposite;
import model.areas.Sitting;
import model.exceptions.CreationException;
import view.MainWindow;
import view.manager.addEvent.AddEventFirst;
import view.manager.addEvent.AddEventSecond;

/**
 * Event controller
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddEventController extends Controller {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public AddEventController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Getter for the continue button of add event step 1
     * 
     * @return The action listener
     */
    public ActionListener getAddEventFirstHandler() {
        return a -> {
            List<String> areaList = new LinkedList<>();
            for (NonComposite n : instance.getMainArea().getNonCompositeWithin()) {
                areaList.add(n.getName() + " " + (n instanceof Sitting ? "(Sitting)" : "(Standing)"));
            }

            AddEventSecond aes = view.getAddEventSecondView();
            aes.update(areaList);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AddEventSecondName);
        };
    }

    /**
     * Getter for the continue button of add event step 2, which adds the event to
     * the app
     * 
     * @return The action listener
     */
    public ActionListener getAddEventSecondHandler() {
        return a -> {
            AddEventFirst aef = view.getAddEventFirstView();
            AddEventSecond aes = view.getAddEventSecondView();

            String category = aef.getCategory();
            Event ev = null;
            try {
                if (category.equals("MUSIC")) {
                    ev = new MusicEvent(aef.getEventName(), aef.getEventTitle(), aef.getDuration(),
                            aef.getDescription(), aef.getAuthor(), aef.getDirector(), aef.getRestriction(),
                            aef.getOrchestra(), aef.getSoloist(), aef.getProgram());
                } else if (category.equals("THEATRE")) {
                    ev = new TheatreEvent(aef.getEventName(), aef.getEventTitle(), aef.getDuration(),
                            aef.getDescription(), aef.getAuthor(), aef.getDirector(), aef.getRestriction(),
                            aef.getActors());
                } else {
                    ev = new DanceEvent(aef.getEventName(), aef.getEventTitle(), aef.getDuration(),
                            aef.getDescription(), aef.getAuthor(), aef.getDirector(), aef.getRestriction(),
                            aef.getOrchestra(), aef.getDancers(), aef.getConductor());
                }
                Map<String, NonComposite> areas = new HashMap<>();
                for (NonComposite nc : instance.getMainArea().getNonCompositeWithin()) {
                    areas.put(nc.getName(), nc);
                }

                for (String areaName : aes.getPrices().keySet()) {
                    ev.addAreaPrice(areas.get(areaName), aes.getPrices().get(areaName));
                }

                instance.addEvent(ev);
                aef.clearFields();

                JOptionPane.showMessageDialog(aes, "Event added succesfully", "Event added",
                        JOptionPane.INFORMATION_MESSAGE);

                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.MenuName);
            } catch (CreationException e) {
                JOptionPane.showMessageDialog(aes,
                        "Some fields contain invalid values or are empty. You will be redirected to the previous page to fix them",
                        "Error adding the event", JOptionPane.ERROR_MESSAGE);
                System.err.println(e);
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.AddEventFirstName);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(aes,
                        "Some prices are empty. Please add a price to every area before continuing",
                        "Error adding the event", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
