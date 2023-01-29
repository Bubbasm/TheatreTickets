package view.customer.reservations;

import javax.swing.*;

import view.shared.TemplateEntry;
import view.shared.FontSize;

import java.awt.*;
import java.awt.event.*;

/**
 * View representing an entry in the list of reservations
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ReservationEntry extends TemplateEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Confimation button */
    private JButton confirm;
    /** Cancel button */
    private JButton cancel;
    /** Text to be displayer */
    private JLabel text;

    /**
     * Constructor of the view
     * 
     * @param info Text to be displayed
     */
    public ReservationEntry(String info) {
        super(80);

        text = new JLabel(info);
        text.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        this.add(text);

        this.add(Box.createGlue());
        confirm = new JButton("Confirm");
        confirm.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));
        this.add(confirm);
        this.add(Box.createHorizontalStrut(10));
        cancel = new JButton("Cancel");
        cancel.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));
        this.add(cancel);
    }

    /**
     * Set the action for the confirm button
     * 
     * @param e action listener
     */
    public void setConfirmHandler(ActionListener e) {
        confirm.addActionListener(e);
    }

    /**
     * Set the action for the cancel button
     * 
     * @param e action listener
     */
    public void setCancelHandler(ActionListener e) {
        cancel.addActionListener(e);
    }

    /**
     * Hides both confirm and continue buttons
     */
    public void hideButtons() {
        cancel.setVisible(false);
        confirm.setVisible(false);
    }

    /**
     * Mimicks a cancelled reservation, unenabling buttons
     */
    public void updateCancel() {
        text.setForeground(Color.RED.darker().darker());
        cancel.setEnabled(false);
        confirm.setEnabled(false);
    }

}
