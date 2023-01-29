package controller.customer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.MainController;
import controller.shared.Controller;
import view.*;
import view.customer.purchase.*;
import model.app.TheatreTickets;
import model.areas.*;

import model.acts.performances.Performance;

/**
 * Controller for the area selector to purhcase tickets
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AreaSelectionController extends Controller implements ActionListener {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public AreaSelectionController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Implementation of the main button (continue button). Note: Perfomance must be
     * set before
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        AreaSelection main = view.getAreaSelectionView();
        Performance selected = mc.getPerformance();
        if (selected == null) {
            JOptionPane.showMessageDialog(main, "An error has ocurred. Please retry again later", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selection = main.getSelection();
        if (selection == null) {
            JOptionPane.showMessageDialog(main, "Please select an area before continuing.", "Area not selected",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        NonComposite a = (NonComposite) instance.searchNonCompositeArea(main.getSelection());

        // Pass information to the next controller
        mc.setNonComposite(a);
        if (selected.isSoldOut(a) == false) {
            int maxNum;
            if (main.isToBeReserved())
                maxNum = Math.min(instance.getMaxTicketsPerReservation(), selected.remainingPositions(a));
            else
                maxNum = Math.min(instance.getMaxTicketsPerPurchase(), selected.remainingPositions(a));

            if (a instanceof Sitting) {
                Sitting sit = (Sitting) a;
                Object[] options = { "Manual", "Recommended" };
                int n = JOptionPane.showOptionDialog(main,
                        "Please choose a method to select the seats for your tickets.", "Seat selection",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (n == JOptionPane.YES_OPTION) { // Manual
                    // Initialize the view
                    ManualSeatSelection mss;
                    mss = new ManualSeatSelection(a.getName(), selected.matrixOccupancy(sit), maxNum);
                    // Add the view to the MainWindow
                    view.updateManualSeatSelectionView(mss);

                    // Set the next view
                    CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                    cl.show(view.getContentPane(), MainWindow.ManualSeatSelectionName);
                } else if (n == JOptionPane.NO_OPTION) { // Automatic

                    // Initialize the view
                    AutomaticSeatSelection ass = new AutomaticSeatSelection(a.getName(), maxNum);
                    ass.setPreviewSeats(selected.matrixOccupancy(sit));
                    // Add the view to the MainWindow
                    view.updateAutomaticSeatSelectionView(ass);

                    // Set the next view
                    CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                    cl.show(view.getContentPane(), MainWindow.AutomaticSeatSelectionName);
                } else { // Closed window
                    return;
                }

            } else { // Standing area selected
                // Initialize the view
                StandingPositionsSelection sps = new StandingPositionsSelection(maxNum);

                // Add the view to the MainWindow
                view.updateStandingPositionsSelectionView(sps);

                // Set the next view
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.StandingPositionsSelectionName);
            }
        } else {
            JOptionPane.showMessageDialog(main,
                    "The area selected is full. Please select a different area before continuing.", "Area is full",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
