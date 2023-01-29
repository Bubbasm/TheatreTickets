package view.customer.purchase;

import java.awt.*;
import javax.swing.*;

import view.shared.*;

/**
 * View to ask number of positions
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class StandingPositionsSelection extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Spinner for positions requested */
    private JSpinner positions;

    /**
     * Constructor
     * 
     * @param maxNum Maximum number of tickets to be considered
     */
    public StandingPositionsSelection(int maxNum) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        JLabel l = new JLabel("Number of tickets: ", JLabel.TRAILING);
        l.setFont(new Font("SansSerif", Font.PLAIN, FontSize.BODY));
        positions = new JSpinner(new SpinnerNumberModel(1, 1, maxNum, 1));
        positions.setMinimumSize(new Dimension(60, 25));
        positions.setMaximumSize(new Dimension(60, 25));
        positions.setPreferredSize(new Dimension(60, 25));

        mainPanel.add(Box.createGlue());
        mainPanel.add(l);
        mainPanel.add(positions);
        mainPanel.add(Box.createGlue());
        this.add(mainPanel, BorderLayout.CENTER);
        this.setTitle("Standing positions");
    }

    /**
     * Getter for requested positions
     * 
     * @return Positions requested
     */
    public int getRequestedPositions() {
        if (positions.getValue() instanceof Number) {
            return ((Number) positions.getValue()).intValue();
        }
        return -1;
    }

}
