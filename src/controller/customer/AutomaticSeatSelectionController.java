package controller.customer;

import java.awt.event.*;

import javax.swing.*;

import controller.MainController;
import view.*;
import view.customer.purchase.*;
import model.acts.performances.Performance;
import model.areas.*;
import model.app.*;
import model.users.*;
import model.areas.positions.*;

import java.util.*;
import view.customer.purchase.Heuristic;

/**
 * Heuristic based seat selection. Implements ActionListener as that is the main
 * action performed. This contoller additionally provides more functionality
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AutomaticSeatSelectionController extends SeatSelectionController {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public AutomaticSeatSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
    }

    /**
     * Getter for preview button action listener
     * 
     * @return the action listener
     */
    public ActionListener getPreviewButtonHandler() {
        return a -> {
            AutomaticSeatSelection ass = view.getAutomaticSeatSelectionView();
            Heuristic viewHeuristic = ass.getHeuristic();
            Performance perf = mc.getPerformance();
            Sitting area = (Sitting) mc.getNonComposite();
            model.enums.Heuristic modelHeuristic = model.enums.Heuristic.valueOf(viewHeuristic.toString());
            int numTicks = ass.getNumberOfSeats();
            int[][] mat = perf.matrixOccupancy(area);
            Set<Seat> seats = perf.getSeats(area, numTicks, modelHeuristic);
            if (seats == null) {
                JOptionPane.showMessageDialog(ass, "There are not enough seats following this heuristic", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (Seat s : seats) {
                mat[s.getRow() - 1][s.getColumn() - 1] = 2;
            }
            ass.setPreviewSeats(mat);
        };
    }

    /**
     * Implementation of the main button (continue button)
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        AutomaticSeatSelection ass = view.getAutomaticSeatSelectionView();
        Heuristic viewHeuristic = ass.getHeuristic();
        Performance perf = mc.getPerformance();
        Sitting area = (Sitting) mc.getNonComposite();
        model.enums.Heuristic modelHeuristic = model.enums.Heuristic.valueOf(viewHeuristic.toString());
        Customer c = (Customer) TheatreTickets.getCurrentCustomer();
        int numTicks = ass.getNumberOfSeats();
        Set<Seat> seats = perf.getSeats(area, numTicks, modelHeuristic);

        if (view.getAreaSelectionView().isToBeReserved()) {
            if (c.reserveTickets(perf, area, seats) == false) {
                JOptionPane.showMessageDialog(ass, "Cannot reserve these seats", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                succesfulReservation(numTicks, ass);
            }
        } else {
            goToPay(seats);
        }
    }
}
