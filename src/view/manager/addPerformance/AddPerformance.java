package view.manager.addPerformance;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import view.shared.SpringUtilities;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for adding a new performance
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddPerformance extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Selector for the event */
    private JComboBox<String> eventSelector;
    /** Date specified */
    private TextField date;
    /** Time speciied */
    private TextField time;

    /**
     * Constructor of the view
     */
    public AddPerformance() {
        super("Add Performance");
        this.setTitle("New Performance");

        JPanel p = new JPanel(new SpringLayout());
        p.setBorder(new EmptyBorder(40, 20, 0, 20));

        JLabel l = new JLabel("Event:", JLabel.TRAILING);
        eventSelector = new JComboBox<>();

        eventSelector.setMinimumSize(new Dimension(300, 25));
        eventSelector.setMaximumSize(new Dimension(300, 25));
        eventSelector.setPreferredSize(new Dimension(300, 25));
        l.setLabelFor(eventSelector);
        p.add(l);
        p.add(eventSelector);

        l = new JLabel("New Date:", JLabel.TRAILING);
        date = new TextField("DD/MM/YYYY");
        date.setMinimumSize(new Dimension(300, date.getPreferredSize().height));
        date.setMaximumSize(new Dimension(300, date.getPreferredSize().height));
        date.setPreferredSize(new Dimension(300, date.getPreferredSize().height));
        l.setLabelFor(date);
        p.add(l);
        p.add(date);

        l = new JLabel("New Time:", JLabel.TRAILING);
        time = new TextField("HH:MM (00:00-23:59h format)");
        time.setMinimumSize(new Dimension(300, time.getPreferredSize().height));
        time.setMaximumSize(new Dimension(300, time.getPreferredSize().height));
        time.setPreferredSize(new Dimension(300, time.getPreferredSize().height));
        l.setLabelFor(time);
        p.add(l);
        p.add(time);

        SpringUtilities.makeCompactGrid(p, 3, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);// padding

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(p);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Updates the view with these events
     * 
     * @param events Events to display
     */
    public void update(List<String> events) {
        eventSelector.removeAllItems();
        for (String e : events) {
            eventSelector.addItem(e);
        }
    }

    /**
     * Getter of the selected event
     * 
     * @return the event name
     */
    public String getEvent() {
        return (String) eventSelector.getSelectedItem();
    }

    /**
     * Getter for the date of the performance
     * 
     * @return the date
     * @throws DateTimeParseException when the date couldnt be parsed (incorrecto
     *                                format or empty)
     */
    public LocalDateTime getDateTime() throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");
        return LocalDateTime.parse(date.getText() + ";" + time.getText(), formatter);
    }

    /** Clearsdate and time fields */
    public void clearFields() {
        date.clearText();
        time.clearText();
    }

}
