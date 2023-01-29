package view.manager.menu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * View for the manager menu
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Menu extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Button to add event */
    private JButton addEventBut;
    /** Button to add cycle */
    private JButton addCycleBut;
    /** Button to add performance */
    private JButton addPerfBut;
    /** Button to edit performance */
    private JButton editPerfBut;
    /** Button to access additional settings */
    private JButton additSettBut;
    /** Button to get statistics */
    private JButton statsBut;
    /** Button to disable seats */
    private JButton disSeatsBut;
    /** Button to modify initially the theatre */
    private JButton modTheatreBut;
    /** Button to log out */
    private JButton logout;

    /**
     * Constructor of the view
     */
    public Menu() {
        JLabel title = new JLabel("Main Menu", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setBorder(new EmptyBorder(30, 20, 0, 20));

        JPanel buts = new JPanel();
        buts.setLayout(new GridLayout(3, 3, 50, 50));
        buts.setBorder(new EmptyBorder(60, 80, 80, 80));

        addEventBut = new JButton("Add Event");
        addEventBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(addEventBut);

        addPerfBut = new JButton("Add Performance");
        addPerfBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(addPerfBut);

        addCycleBut = new JButton("Add Cycle");
        addCycleBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(addCycleBut);

        disSeatsBut = new JButton("Disable Seats");
        disSeatsBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(disSeatsBut);

        editPerfBut = new JButton("Edit Performances");
        editPerfBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(editPerfBut);

        additSettBut = new JButton("Additional Settings");
        additSettBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(additSettBut);

        statsBut = new JButton("See Statistics");
        statsBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(statsBut);

        modTheatreBut = new JButton("Modify Theatre");
        modTheatreBut.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(modTheatreBut);

        logout = new JButton("Log out");
        logout.setFont(new Font("SansSerif", Font.BOLD, 20));
        buts.add(logout);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.PAGE_START);
        this.add(buts, BorderLayout.CENTER);
    }

    /**
     * set the handler for the add event button
     * 
     * @param c action listener
     */
    public void setAddEventHandler(ActionListener c) {
        addEventBut.addActionListener(c);
    }

    /**
     * set the handler for the add cycle button
     * 
     * @param c action listener
     */
    public void setAddCycleHandler(ActionListener c) {
        addCycleBut.addActionListener(c);
    }

    /**
     * set the handler for the add performance button
     * 
     * @param c action listener
     */
    public void setAddPerformanceHandler(ActionListener c) {
        addPerfBut.addActionListener(c);
    }

    /**
     * set the handler for the modify theatre button
     * 
     * @param c action listener
     */
    public void setModifyTheatreHandler(ActionListener c) {
        modTheatreBut.addActionListener(c);
    }

    /**
     * set the handler for the disable seats button
     * 
     * @param c action listener
     */
    public void setDisableSeatsHandler(ActionListener c) {
        disSeatsBut.addActionListener(c);
    }

    /**
     * set the handler for the edit performances button
     * 
     * @param c action listener
     */
    public void setEditPerformancesHandler(ActionListener c) {
        editPerfBut.addActionListener(c);
    }

    /**
     * set the handler for the additional settings button
     * 
     * @param c action listener
     */
    public void setAdditionalSettingsHandler(ActionListener c) {
        additSettBut.addActionListener(c);
    }

    /**
     * set the handler for the statistics button
     * 
     * @param c action listener
     */
    public void setStatisticsHandler(ActionListener c) {
        statsBut.addActionListener(c);
    }

    /**
     * set the handler for the logout button
     * 
     * @param c action listener
     */
    public void setLogoutHandler(ActionListener c) {
        logout.addActionListener(c);
    }

}
