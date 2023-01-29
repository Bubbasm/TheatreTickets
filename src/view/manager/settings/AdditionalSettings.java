package view.manager.settings;

import javax.swing.*;

import view.shared.FontSize;
import view.shared.SpringUtilities;
import view.shared.TemplatePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * View for the additional settings
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AdditionalSettings extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** spinner with the maximum number of tickets that can be purchased at once */
    private JSpinner maxPerPurch;
    /** spinner with the maximum number of tickets that can be reserved at once */
    private JSpinner maxPerReserv;
    /** spinner with the max hours to make/pay a reservation */
    private JSpinner maxHours;

    /**
     * Constructor of the view
     * 
     */
    public AdditionalSettings() {
        super("Save changes");
        this.setTitle("Additional Settings");

        JPanel settings = new JPanel(new SpringLayout());

        JLabel l = new JLabel("Maximum tickets a user can buy at once:", JLabel.TRAILING);
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxPerPurch = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        maxPerPurch.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxPerPurch.setMinimumSize(new Dimension(60, 25));
        maxPerPurch.setMaximumSize(new Dimension(60, 25));
        maxPerPurch.setPreferredSize(new Dimension(60, 25));
        l.setLabelFor(maxPerPurch);
        settings.add(l);
        settings.add(maxPerPurch);

        l = new JLabel("Maximum tickets a user can reserve at once:", JLabel.TRAILING);
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxPerReserv = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        maxPerReserv.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxPerReserv.setMinimumSize(new Dimension(60, 25));
        maxPerReserv.setMaximumSize(new Dimension(60, 25));
        maxPerReserv.setPreferredSize(new Dimension(60, 25));
        l.setLabelFor(maxPerReserv);
        settings.add(l);
        settings.add(maxPerReserv);

        l = new JLabel("Hours for reservations to be made and paid within:", JLabel.TRAILING);
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxHours = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        maxHours.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        maxHours.setMinimumSize(new Dimension(60, 25));
        maxHours.setMaximumSize(new Dimension(60, 25));
        maxHours.setPreferredSize(new Dimension(60, 25));
        l.setLabelFor(maxHours);
        settings.add(l);
        settings.add(maxHours);

        SpringUtilities.makeCompactGrid(settings, 3, 2, // rows, cols
                0, 0, // initX, initY
                10, 10, // xPad, yPad
                0, new ArrayList<>()); // not used

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(settings);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Update the values of the spinners
     * 
     * @param perPurch  current maximum in the app
     * @param perReserv current maximum in the app
     * @param maxH      current maximum in the app
     */
    public void update(int perPurch, int perReserv, int maxH) {
        maxPerPurch.setValue(perPurch);
        maxPerReserv.setValue(perReserv);
        maxHours.setValue(maxH);
    }

    /**
     * get the maximum number of tickets per purchase
     * 
     * @return the max number
     */
    public int getMaxTicketsPerPurchase() {
        return (int) maxPerPurch.getValue();
    }

    /**
     * get the maximum number of tickets per reservation
     * 
     * @return the max number
     */
    public int getMaxTicketsPerReservation() {
        return (int) maxPerReserv.getValue();
    }

    /**
     * get the maximum hours to reserve and pay
     * 
     * @return the max hours
     */
    public int getHoursForReservation() {
        return (int) maxHours.getValue();
    }

}
