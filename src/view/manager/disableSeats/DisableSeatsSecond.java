package view.manager.disableSeats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import view.shared.SeatsPanel;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for disabling seats (part 2/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class DisableSeatsSecond extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Date from */
    private TextField from;
    /** Date to */
    private TextField to;
    /** Panel displaying and selecting seats */
    private SeatsPanel seats;

    /**
     * Constructor of the view
     * 
     * @param areaName string with the name of the area
     * @param seatArr  array that represents the state of the area
     */
    public DisableSeatsSecond(String areaName, int[][] seatArr) {
        super("Disable seats");
        this.setTitle("Disable Seats (2/2)");
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

        JLabel l = new JLabel("From:", JLabel.TRAILING);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        from = new TextField("DD/MM/YYYY");
        from.setMinimumSize(new Dimension(90, from.getPreferredSize().height));
        from.setMaximumSize(new Dimension(90, from.getPreferredSize().height));
        from.setPreferredSize(new Dimension(90, from.getPreferredSize().height));
        l.setLabelFor(from);
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(from);

        l = new JLabel("To:", JLabel.TRAILING);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        to = new TextField("DD/MM/YYYY");
        to.setMinimumSize(new Dimension(90, to.getPreferredSize().height));
        to.setMaximumSize(new Dimension(90, to.getPreferredSize().height));
        to.setPreferredSize(new Dimension(90, to.getPreferredSize().height));
        l.setLabelFor(to);
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(to);
        top.add(Box.createHorizontalGlue());

        JPanel infoLayer = new JPanel();
        infoLayer.setLayout(new BoxLayout(infoLayer, BoxLayout.Y_AXIS));
        infoLayer.setBorder(new EmptyBorder(0, 0, 0, 15));
        infoLayer.add(Box.createVerticalGlue());

        JLabel l1 = new JLabel("In grey disabled seats");
        l1.setFont(new Font("SansSerif", Font.BOLD, 15));
        l1.setForeground(Color.GRAY);
        infoLayer.add(l1);

        JLabel l2 = new JLabel("In green not disabled seats");
        l2.setFont(new Font("SansSerif", Font.BOLD, 15));
        l2.setForeground(Color.GREEN.darker());
        infoLayer.add(l2);

        JLabel l3 = new JLabel("In blue selected seats");
        l3.setFont(new Font("SansSerif", Font.BOLD, 15));
        l3.setForeground(Color.BLUE);
        infoLayer.add(l3);
        infoLayer.add(Box.createVerticalGlue());

        seats = new SeatsPanel(areaName, seatArr);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.add(infoLayer);
        bottom.add(seats);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(20, 20, 30, 40));
        center.add(top);
        center.add(Box.createVerticalStrut(30));
        center.add(bottom);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Getter for the first date (From)
     * 
     * @return the date
     * @throws DateTimeParseException when the date could not be parsed
     */
    public LocalDateTime getFrom() throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");
        return LocalDateTime.parse(from.getText() + ";00:00", formatter);
    }

    /**
     * Getter for the second date (To)
     * 
     * @return the date
     * @throws DateTimeParseException when the date could not be parsed
     */
    public LocalDateTime getTo() throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");
        return LocalDateTime.parse(to.getText() + ";23:59", formatter);
    }

    /**
     * Getter for selected seats to disable
     * 
     * @return 1 represents selected (to be disabled) and 0 not selected
     */
    public int[][] getSelectedSeats() {
        return seats.getSelectedSeats();
    }

}
