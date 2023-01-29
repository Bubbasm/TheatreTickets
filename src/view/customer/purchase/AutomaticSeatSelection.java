package view.customer.purchase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import view.shared.*;

/**
 * View in charge of selecting seats based on heuristics
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AutomaticSeatSelection extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Number of seats requested */
    private int numberOfSeats;
    /** Heuristic used */
    private Heuristic heuristic;
    /** Preview button to display selection */
    private JButton previewButton;
    /** Panel containing preview show */
    private JPanel preview;
    /** Area's name */
    private String areaName;

    /**
     * Constructor
     * 
     * @param areaName Name of the area
     * @param maxNum   Maximum number of seats
     */
    public AutomaticSeatSelection(String areaName, int maxNum) {
        this.setTitle("Please select a heuristic for seat selection");
        this.areaName = areaName;

        JRadioButton[] buttons = { new JRadioButton("Seats centered in lower rows (near stage)", true),
                new JRadioButton("Seats centered in middle rows", false),
                new JRadioButton("Seats centered in higher rows (far from stage)", false),
                new JRadioButton("Seats furthest away from already bought seats", false) };

        ButtonGroup radioButtons = new ButtonGroup();
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        this.heuristic = Heuristic.CENTERED_LOW;
        for (int i = 0; i < 4; i++) {
            JRadioButton b = buttons[i];
            b.setFont(new Font("SansSerif", Font.BOLD, 15));
            radioButtons.add(b);
            leftPanel.add(b);
            switch (i) {
            case 0:
                b.addActionListener(e -> this.heuristic = Heuristic.CENTERED_LOW);
                break;
            case 1:
                b.addActionListener(e -> this.heuristic = Heuristic.CENTERED_MID);
                break;
            case 2:
                b.addActionListener(e -> this.heuristic = Heuristic.CENTERED_HIGH);
                break;
            default:
                b.addActionListener(e -> this.heuristic = Heuristic.FAR_FROM_OTHERS);
                break;
            }
        }

        this.numberOfSeats = 1;
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, maxNum, 1));
        spinner.addChangeListener(e -> numberOfSeats = (int) spinner.getValue());
        spinner.setMinimumSize(new Dimension(60, 40));
        spinner.setMaximumSize(new Dimension(60, 40));

        JLabel spinnerText = new JLabel("Number of seats: ", SwingConstants.LEFT);
        spinnerText.setFont(new Font("SansSerif", Font.BOLD, 15));
        spinnerText.setBackground(this.getBackground());

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.X_AXIS));
        spinnerPanel.setBorder(new EmptyBorder(20, 40, 20, 20));
        spinner.setFont(spinnerText.getFont());
        spinnerPanel.add(spinnerText);
        spinnerPanel.add(spinner);
        spinnerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(new EmptyBorder(0, 40, 0, 80));
        rightPanel.add(spinnerPanel);

        previewButton = new JButton("Preview");
        previewButton.setFont(new Font("SansSerif", Font.BOLD, 15));

        JPanel topPanel = new JPanel();
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);
        topPanel.add(previewButton);

        JPanel bottomPanel = new JPanel();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(topPanel);
        centerPanel.add(bottomPanel);

        JPanel outterPanel = new JPanel();
        outterPanel.setLayout(new BoxLayout(outterPanel, BoxLayout.Y_AXIS));
        preview = new JPanel(new BorderLayout());
        preview.setBorder(new EmptyBorder(0, 100, 0, 100));

        outterPanel.add(centerPanel);
        outterPanel.add(preview);

        this.add(outterPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the preview seat selection
     * 
     * @param seat Matrix representing seats selected
     */
    public void setPreviewSeats(int[][] seat) {
        preview.removeAll();
        preview.add(new SeatsPanel(areaName, seat, false), BorderLayout.CENTER);
        preview.revalidate();
        preview.repaint();
    }

    /**
     * Getter for heuristic selected
     * 
     * @return The heuristic
     */
    public Heuristic getHeuristic() {
        return this.heuristic;
    }

    /**
     * Getter for the number of seats
     * 
     * @return Number of seats requested
     */
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    /**
     * Sets the action performed when pressing the preview button
     * 
     * @param c Listener
     */
    public void setPreviewButtonHandler(ActionListener c) {
        previewButton.addActionListener(c);
    }

}
