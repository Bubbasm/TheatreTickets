package controller.manager;

import model.app.TheatreTickets;
import model.areas.Area;
import model.areas.Composite;
import model.areas.NonComposite;
import model.areas.Sitting;
import model.areas.Standing;
import view.MainWindow;
import view.manager.modifyTheatre.ModifyTheatreFirst;
import view.manager.modifyTheatre.ModifyTheatreSecond;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

import controller.MainController;
import controller.shared.Controller;

/**
 * Controller for modifying theatre
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ModifyTheatreController extends Controller {
    /** Main controller */
    private MainController mc;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller
     */
    public ModifyTheatreController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view);
        this.mc = mc;
    }

    /**
     * Getter for adding an area having selected some place
     * 
     * @return The action listener
     */
    public ActionListener getAddAreaFirstHandler() {
        return l -> {
            ModifyTheatreFirst mtf = view.getModifyTheatreFirstView();
            if (mtf.getSelection() == null) {
                JOptionPane.showMessageDialog(mtf, "Please select an area before continuing.", "Area not selected",
                        JOptionPane.ERROR_MESSAGE);
            } else if (instance.searchArea(mtf.getSelection()) instanceof NonComposite) {
                JOptionPane.showMessageDialog(mtf, "Cannot add subarea to a Non-Composite area!", "Incorrect area",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                ModifyTheatreSecond mts = view.getModifyTheatreSecondView();
                mts.emptyFields();

                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.ModifyTheatreSecondName);
            }
        };
    }

    /**
     * Getter for adding an area specifying details
     * 
     * @return The action listener
     */
    public ActionListener getAddAreaSecondHandler() {
        return l -> {
            ModifyTheatreFirst mtf = view.getModifyTheatreFirstView();
            ModifyTheatreSecond mts = view.getModifyTheatreSecondView();

            if (mts.getAreaName().equals("")) {
                JOptionPane.showMessageDialog(mts, "The name of the area cannot be empty", "Incorrect name",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if (instance.searchArea(mts.getAreaName()) != null) {
                JOptionPane.showMessageDialog(mts,
                        "This area name is already taken.\nPlease change the name in order to add this area",
                        "Incorrect name", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (mtf.getSelection() == null) {
                JOptionPane.showMessageDialog(mts, "Area not selected", "Area not selected", JOptionPane.ERROR_MESSAGE);
            }

            // area already exists as it is checked in the previous view
            Area a = instance.searchArea(mtf.getSelection());
            if (mts.getAreaType().equals("Composite")) { // composite
                ((Composite) a).addArea(new Composite(mts.getAreaName()));
            } else {
                String cap = mts.getAreaCapacity();
                int rows, cols, n;
                NonComposite newArea;
                double price;
                try {
                    price = Double.parseDouble(mts.getPassPrice());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mts, "Incorrect price format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cap.matches("[0-9]+x[0-9]+")) { // sitting
                    try {
                        rows = Integer.parseInt(cap.split("x")[0]);
                        cols = Integer.parseInt(cap.split("x")[1]);
                        if (rows * cols == 0)
                            throw new NumberFormatException();
                        newArea = new Sitting(mts.getAreaName(), rows, cols);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(mts, "Incorrect capacity format", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else if (cap.matches("[0-9]+")) { // standing
                    try {
                        n = Integer.parseInt(cap);
                        if (n == 0)
                            throw new NumberFormatException();
                        newArea = new Standing(mts.getAreaName(), n);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(mts, "Incorrect capacity format", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else { // error
                    JOptionPane.showMessageDialog(mts, "Incorrect capacity format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // must be done before adding to theatre tickets
                ((Composite) a).addArea(newArea);
                instance.addArea(newArea, price);
            }
            JOptionPane.showMessageDialog(mts, "Area added successfuly", "Area added", JOptionPane.INFORMATION_MESSAGE);
            view.updateModifyTheatreFirstView(
                    new ModifyTheatreFirst(mc.getTreePanelController().createTree("Theatre")));
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.ModifyTheatreFirstName);
        };
    }

    /**
     * Getter for back button, to not add an area
     * 
     * @return The action listener
     */
    public ActionListener getBackButtonHandler() {
        return l -> {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.ModifyTheatreFirstName);
        };
    }
}
