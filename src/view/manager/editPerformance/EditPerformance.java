package view.manager.editPerformance;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.List;

import view.shared.SpringUtilities;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for editing performances
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EditPerformance extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Seletor for performance */
    private JComboBox<String> perfSelector;
    /** Button to postpone the selected performance */
    private JButton postponePerf;
    /** Button to cancel the selected performance */
    private JButton cancelPerf;
    /** Date to postpone */
    private TextField date;
    /** Time to postpone */
    private TextField time;

    /**
     * Constructor of the view
     */
    public EditPerformance() {
        super(false);
        this.setTitle("Edit Performance");

        JPanel p = new JPanel(new SpringLayout());
        p.setBorder(new EmptyBorder(40, 20, 0, 20));

        JLabel l = new JLabel("Performance:", JLabel.TRAILING);
        perfSelector = new JComboBox<>();
        perfSelector.setMinimumSize(new Dimension(300, 25));
        perfSelector.setMaximumSize(new Dimension(300, 25));
        perfSelector.setPreferredSize(new Dimension(300, 25));
        l.setLabelFor(perfSelector);
        p.add(l);
        p.add(perfSelector);

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

        postponePerf = new JButton("Postpone Performance");
        p.add(Box.createRigidArea(new Dimension(10, 25)));
        p.add(postponePerf);
        cancelPerf = new JButton("Cancel Performance");
        p.add(Box.createRigidArea(new Dimension(10, 25)));
        p.add(cancelPerf);

        SpringUtilities.makeCompactGrid(p, 5, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);// padding

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(p);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Updates the combobox adding items performances to select
     * 
     * @param perfs List of performances
     */
    public void update(List<String> perfs) {
        perfSelector.removeAllItems();
        for (String per : perfs) {
            perfSelector.addItem(per);
        }
    }

    /**
     * Set the handler for the cancel button
     * 
     * @param a action listener
     */
    public void setCancelPerformanceHandler(ActionListener a) {
        cancelPerf.addActionListener(a);
    }

    /**
     * Set the handler for the postpone button
     * 
     * @param a action listener
     */
    public void setPostponePerformanceHandler(ActionListener a) {
        postponePerf.addActionListener(a);
    }

    /**
     * Getter for the new date
     * 
     * @return the date
     * @throws DateTimeParseException if the date couldnt be parsed
     */
    public LocalDateTime getNewDateTime() throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");
        return LocalDateTime.parse(date.getText() + ";" + time.getText(), formatter);
    }

    /**
     * Getter for the event
     * 
     * @return the event title
     */
    public String getEventTitle() {
        String aux = ((String) perfSelector.getSelectedItem());
        return aux.split(" at ")[0];
    }

    /**
     * Caluclates the real date from the given perfomance string
     * 
     * @return The date
     */
    public LocalDateTime getActualDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String aux = ((String) perfSelector.getSelectedItem());
        return LocalDateTime.parse(aux.split(" at ")[1], formatter);
    }

}
