package view.manager.addCycle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import view.manager.templates.EventEntrySelection;
import view.shared.EntryList;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for adding a new cycle (part 1/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddCycleFirst extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** text field with the name of the event */
    private TextField name;
    /** map that associates an event to its corresponding entry in the list */
    private Map<String, EventEntrySelection> events;

    /**
     * Constructor of the view
     * 
     * @param eventsList list with all the available events at the moment
     */
    public AddCycleFirst(List<String> eventsList) {
        super("Add Cycle");
        this.setTitle("Add Cycle (1/2)");
        events = new HashMap<>();

        JPanel aux = new JPanel();
        aux.setLayout(new BoxLayout(aux, BoxLayout.X_AXIS));
        aux.setBorder(new EmptyBorder(20, 0, 30, 0));

        JLabel l = new JLabel("Name of the cycle:", JLabel.TRAILING);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        name = new TextField("Name");
        name.setMaximumSize(new Dimension(200, name.getPreferredSize().height));
        name.setPreferredSize(new Dimension(200, name.getPreferredSize().height));
        l.setLabelFor(name);
        aux.add(l);
        aux.add(name);

        EntryList p = new EntryList();
        p.removeScrollBorder();
        for (String ev : eventsList) {
            EventEntrySelection ees = new EventEntrySelection(ev);
            events.put(ev, ees);
            p.addEntry(ees);
        }

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(aux);
        center.add(p);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Getter fot the cycle name
     * 
     * @return the cycle name
     */
    public String getCycleName() {
        return name.getText();
    }

    /**
     * Getter for the events that have been selected
     * 
     * @return a list with the selected events
     */
    public List<String> getEvents() {
        List<String> res = new LinkedList<>();
        for (String s : events.keySet()) {
            if (events.get(s).isSelected())
                res.add(s);
        }
        return res;
    }

}
