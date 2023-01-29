package controller.customer;

import java.awt.event.*;

import javax.swing.*;

import controller.MainController;
import view.*;
import view.customer.purchase.*;
import model.app.TheatreTickets;
import model.areas.*;
import model.users.Customer;
import model.areas.positions.*;
import model.acts.performances.*;

import java.util.*;

/**
 * Controller for the mannual seat selection
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ManualSeatSelectionController extends SeatSelectionController {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller
     */
    public ManualSeatSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
    }

    /**
     * Implements actions performed when requesting specified seats. Note: area and
     * performance must be set before
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        ManualSeatSelection mss = view.getManualSeatSelectionView();
        Sitting area = (Sitting) mc.getNonComposite();
        int[][] mat = mss.getSelectedSeats();
        int numTicks = 0;
        Set<Seat> seats = new HashSet<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 1) {
                    seats.add(area.getSeat(i + 1, j + 1));
                    numTicks++;
                }
            }
        }

        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        Performance perf = mc.getPerformance();
        if (view.getAreaSelectionView().isToBeReserved()) {
            if (c.reserveTickets(perf, area, seats) == false) {
                JOptionPane.showMessageDialog(mss, "Cannot reserve these seats", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                succesfulReservation(numTicks, mss);
            }
        } else {
            goToPay(seats);
        }
    }
}
