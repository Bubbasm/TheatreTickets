package view.manager.templates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import view.shared.TemplateEntry;

/**
 * Entries in which we select an event with a checkbox
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EventEntrySelection extends TemplateEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Event entry to be added or not */
    private JCheckBox selected;

    /**
     * Constructor
     * 
     * @param name Event name
     */
    public EventEntrySelection(String name) {
        super(30);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel p = new JPanel(new GridLayout(1, 2));
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));

        selected = new JCheckBox();
        left.add(Box.createHorizontalGlue());
        left.add(selected);
        left.add(Box.createHorizontalStrut(15));

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.add(new JLabel(name, JLabel.LEADING));
        right.add(Box.createHorizontalGlue());

        p.add(left);
        p.add(right);

        this.add(p);
    }

    /**
     * Is the checkbox selected?
     * 
     * @return true if the event has been selected, false otherwise
     */
    public boolean isSelected() {
        return selected.isSelected();
    }
}
