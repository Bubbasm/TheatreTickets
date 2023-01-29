package view.shared;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

/**
 * Panel that shows the state of a sitting area (selecting/disabling seats for
 * example)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SeatsPanel extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Matrix of seats */
    private int seat[][];
    /** Current number of selected seats */
    private int currentNum = 0;

    /**
     * Constructor
     * 
     * @param areaName name of the area
     * @param seats    array representing the state of the area
     * @param maxNum   maximum number of seats that can be selected
     */
    public SeatsPanel(String areaName, int[][] seats, int maxNum) {
        this(areaName, seats, true, maxNum);
    }

    /**
     * Constructor
     * 
     * @param areaName name of the area
     * @param seats    array representing the state of the area
     */
    public SeatsPanel(String areaName, int[][] seats) {
        this(areaName, seats, true, seats.length * seats[0].length);
    }

    /**
     * Constructor
     * 
     * @param areaName name of the area
     * @param seats    array representing the state of the area
     * @param editable true if the seats can be selected
     */
    public SeatsPanel(String areaName, int[][] seats, boolean editable) {
        this(areaName, seats, editable, seats.length * seats[0].length);
    }

    /**
     * Constructor
     * 
     * @param areaName name of the area
     * @param seats    array representing the state of the area
     * @param editable true if the seats can be selected
     * @param maxNum   maximum number of seats that can be selected
     */
    public SeatsPanel(String areaName, int[][] seats, boolean editable, int maxNum) {
        seat = seats;
        this.setLayout(new GridLayout(seat.length, seat[0].length, 10, 10));
        this.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 3), areaName),
                new EmptyBorder(20, 20, 20, 20)));
        this.setPreferredSize(new Dimension(this.getPreferredSize().width / 3, this.getPreferredSize().height));

        for (int i = 0; i < seat.length; i++) {
            for (int j = 0; j < seat[0].length; j++) {
                MyButton sit = new MyButton(i, j);
                Color c;
                if (seat[i][j] == 0)
                    c = Color.RED.darker();
                else if (seat[i][j] == 1)
                    c = Color.GREEN.darker();

                else if (seat[i][j] == 2) {
                    c = Color.BLUE.darker();
                    currentNum++;
                } else {
                    c = Color.GRAY.darker();
                }
                sit.setBackground(c);
                if (editable)
                    sit.addActionListener(e -> {
                        if (sit.getBackground().equals(Color.GREEN.darker()) && currentNum < maxNum) {
                            sit.setBackground(Color.BLUE.darker());
                            currentNum++;
                            seat[sit.i][sit.j] = 2;
                        } else if (sit.getBackground().equals(Color.BLUE.darker())) {
                            currentNum--;
                            sit.setBackground(Color.GREEN.darker());
                            seat[sit.i][sit.j] = 1;
                        }
                    });
                else
                    sit.setEnabled(false);
                sit.setBorder(new EmptyBorder(5, 5, 5, 5));
                this.add(sit);
            }
        }

    }

    /**
     * Private class for the buttons that represent each seat
     */
    private class MyButton extends JButton {
        /** Serialized class */
        private static final long serialVersionUID = 1L;
        protected int i, j;

        /**
         * Constructor
         * 
         * @param i row
         * @param j column
         */
        public MyButton(int i, int j) {
            super();
            this.i = i;
            this.j = j;
        }
    }

    /**
     * Get the seats that are selected
     * 
     * @return An array representing the new state of the area. 0 if not selected 1
     *         if selected
     */
    public int[][] getSelectedSeats() {
        int[][] selected = new int[seat.length][seat[0].length];
        for (int i = 0; i < seat.length; i++) {
            for (int j = 0; j < seat[0].length; j++) {
                if (seat[i][j] == 2)
                    selected[i][j] = 1;
            }
        }
        return selected;
    }
}
