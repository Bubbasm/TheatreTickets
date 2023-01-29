package view.customer.search;

import view.shared.TemplateEntry;
import view.shared.FontSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * View representing an entry in the search view
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class SearchEntry extends TemplateEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Button for search entry */
    protected JButton button;
    /** Generic text representing entry */
    protected JLabel text;

    /**
     * Constructor of the view
     */
    public SearchEntry() {
        super(75);

        this.button = new JButton();
        this.button.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));
        this.text = new JLabel("");
        this.text.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        this.add(text);
        this.add(Box.createGlue());
        this.add(button);
    }

    /**
     * set the handler of the button in the entry
     * 
     * @param c action listener
     */
    public void setButtonHandler(ActionListener c) {
        this.button.addActionListener(c);
    }

    /**
     * set the action command for the button
     * 
     * @param str Action command
     */
    public void setButtonActionCommand(String str) {
        button.setActionCommand(str);
    }

    /**
     * hide the button
     */
    public void hideButton() {
        this.button.setVisible(false);
    }

    /**
     * grey out the button
     */
    public void greyOutButton() {
        this.button.setEnabled(false);
    }
}
