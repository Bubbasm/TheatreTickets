package view.customer.search;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import view.shared.FontSize;

/**
 * View representing the search view for a unregistered customer
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SearchUnregistered extends Search {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** button that redirects you to the log in screen */
    private JButton logInNow;

    /**
     * Constructor of the view
     */
    public SearchUnregistered() {
        super();
        JPanel bottomLayer = new JPanel();
        JLabel info = new JLabel("Not registered. Some functionalities are not available");
        info.setFont(new Font("SansSerif", Font.PLAIN, FontSize.SUBTITLE));
        logInNow = new JButton("Log in now");

        bottomLayer.setLayout(new BoxLayout(bottomLayer, BoxLayout.LINE_AXIS));
        bottomLayer.setBorder(new EmptyBorder(5, 5, 5, 5));
        bottomLayer.add(logInNow);
        bottomLayer.add(Box.createGlue());
        bottomLayer.add(info);
        this.add(bottomLayer, BorderLayout.PAGE_END);
    }

    /**
     * set the handler for the log in button
     * 
     * @param c action listener
     */
    public void setLogInNowHandler(ActionListener c) {
        logInNow.addActionListener(c);
    }
}
