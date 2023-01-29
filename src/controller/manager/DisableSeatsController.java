package controller.manager;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import model.app.TheatreTickets;
import view.MainWindow;

import javax.swing.*;

import controller.shared.Controller;
import model.areas.NonComposite;
import model.areas.Sitting;
import model.areas.Standing;
import view.manager.disableSeats.*;

import java.awt.*;

/**
 * Controller in charge of disabling seats
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class DisableSeatsController extends Controller {
    /** Area to consider to disable */
    private Sitting areaToConsider;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public DisableSeatsController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Getter for disabling seats step 1 (first screen) Note: an area must be
     * selected
     * 
     * @return The action listener
     */
    public ActionListener getContinueButtonFirstHandler() {
        return a -> {
            DisableSeatsFirst dsf = view.getDisableSeatsFirstView();
            NonComposite area = instance.searchNonCompositeArea(dsf.getSelection());
            if (area == null) {
                JOptionPane.showMessageDialog(dsf, "Please select an area before continuing.", "Area not selected",
                        JOptionPane.ERROR_MESSAGE);
            } else if (area instanceof Standing) {
                JOptionPane.showMessageDialog(dsf, "There are no seats in a Standing area!!", "Incorrect area",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                areaToConsider = (Sitting) area;
                DisableSeatsSecond dss = new DisableSeatsSecond(area.getName(), areaToConsider.matrixOccupancy());

                view.updateDisableSeatsSecondView(dss);

                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.DisableSeatsSecondName);
            }
        };
    }

    /**
     * Getter for disabling seats step 2 (final step)
     * 
     * @return The action listener
     */
    public ActionListener getContinueButtonSecondHandler() {
        return a -> {
            DisableSeatsSecond dss = view.getDisableSeatsSecondView();

            Sitting area = areaToConsider;
            int[][] seats = dss.getSelectedSeats();

            try {
                LocalDateTime from = dss.getFrom();
                LocalDateTime to = dss.getTo();
                for (int i = 0; i < seats.length; i++) {
                    for (int j = 0; j < seats[0].length; j++) {
                        if (seats[i][j] == 1)
                            instance.disableSeat(area, i + 1, j + 1, from, to);
                    }
                }
                JOptionPane.showMessageDialog(dss, "Seats disabled succesfully", "Seats disabled",
                        JOptionPane.INFORMATION_MESSAGE);
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.MenuName);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(dss, "Invalid format in the dates", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
