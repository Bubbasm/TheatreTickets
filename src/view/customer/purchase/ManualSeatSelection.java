package view.customer.purchase;

import javax.swing.*;
import javax.swing.border.*;

import view.shared.*;

import java.awt.*;

/**
 * View representing a manual seat selection with buttons
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ManualSeatSelection extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Panel containing the buttons */
    private SeatsPanel selectedSeats;

    /**
     * Contructor
     *
     * @param areaName     Name of the area
     * @param seat         Matrix representing information about the area's
     *                     occupancy
     * @param maxSelection Maximum number of seats that can be selected
     */
    public ManualSeatSelection(String areaName, int[][] seat, int maxSelection) {

        this.setTitle("Selected seats");

        JPanel contentLayer = new JPanel(new BorderLayout());
        JPanel infoLayer = new JPanel();
        infoLayer.setLayout(new BoxLayout(infoLayer, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("In red taken seats");
        l1.setFont(new Font("SansSerif", Font.BOLD, 15));
        l1.setForeground(Color.RED);
        infoLayer.add(l1);

        JLabel l2 = new JLabel("In green available seats");
        l2.setFont(new Font("SansSerif", Font.BOLD, 15));
        l2.setForeground(Color.GREEN.darker());
        infoLayer.add(l2);

        JLabel l3 = new JLabel("In blue selected seats");
        l3.setFont(new Font("SansSerif", Font.BOLD, 15));
        l3.setForeground(Color.BLUE);
        infoLayer.add(l3);

        JLabel l4 = new JLabel("In grey disabled seats");
        l4.setFont(new Font("SansSerif", Font.BOLD, 15));
        l4.setForeground(Color.GRAY);
        infoLayer.add(l4);

        infoLayer.setBorder(new EmptyBorder(10, 10, 5, 5));

        selectedSeats = new SeatsPanel(areaName, seat, maxSelection);
        contentLayer.add(selectedSeats);

        this.setTitle("Manual seat selection");
        this.add(infoLayer, BorderLayout.WEST);
        this.add(contentLayer, BorderLayout.CENTER);
        JPanel space;
        space = new JPanel();
        space.setPreferredSize(new Dimension(infoLayer.getPreferredSize().width, 0));
        this.add(space, BorderLayout.EAST);
        space = new JPanel();
        space.setPreferredSize(new Dimension(125, 0));

    }

    /**
     * Getter for the selected seats
     * 
     * @return Matrix representing the chosen seats
     */
    public int[][] getSelectedSeats() {
        return selectedSeats.getSelectedSeats();
    }
}
