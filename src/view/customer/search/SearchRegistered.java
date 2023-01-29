
package view.customer.search;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

/**
 * View representing the search view for a registered customer
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SearchRegistered extends Search {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** button that redirects you to the notifications */
    private JButton notifications;
    /** button that redirects you to the reservations */
    private JButton myReservations;
    /** button to log out */
    private JButton logout;

    /**
     * Constructor of the view
     */
    public SearchRegistered() {
        super();
        JPanel bottomLayer = new JPanel();
        logout = new JButton("Log out");
        bottomLayer.setLayout(new BoxLayout(bottomLayer, BoxLayout.LINE_AXIS));
        bottomLayer.setBorder(new EmptyBorder(5, 5, 5, 5));

        notifications = new JButton("Notifications");
        myReservations = new JButton("See my reservations");
        bottomLayer.add(logout);
        bottomLayer.add(Box.createGlue());
        bottomLayer.add(myReservations);
        bottomLayer.add(Box.createHorizontalStrut(10));
        bottomLayer.add(notifications);
        this.add(bottomLayer, BorderLayout.PAGE_END);
    }

    /**
     * set the handler of the notification button
     * 
     * @param c action listener
     */
    public void setNotificationHandler(ActionListener c) {
        notifications.addActionListener(c);
    }

    /**
     * set the handler for the log out button
     * 
     * @param c action listener
     */
    public void setLogOutHandler(ActionListener c) {
        logout.addActionListener(c);
    }

    /**
     * set handler for the reservations handler
     * 
     * @param c action listener
     */
    public void setReservationsHandler(ActionListener c) {
        myReservations.addActionListener(c);
    }
}
