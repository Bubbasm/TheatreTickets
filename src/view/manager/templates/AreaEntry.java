package view.manager.templates;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import view.shared.TemplateEntry;
import view.shared.TextField;

/**
 * Template for the entries in addEvent/addCycle
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AreaEntry extends TemplateEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Text field containing the information depending on the type */
    private TextField txtField;
    /** Type PRICE for area entry */
    public static final String PRICE = "Price";
    /** Type DISCOUNT for area entry */
    public static final String DISCOUNT = "Discount";

    /**
     * Constructor for the view
     * 
     * @param areaString name of the area
     * @param type       Price/Discount
     */
    public AreaEntry(String areaString, String type) {
        super(50);
        txtField = new TextField(type);
        txtField.setHorizontalAlignment(SwingConstants.RIGHT);
        int sz = type.equals(PRICE) ? 50 : 70;
        txtField.setMinimumSize(new Dimension(sz, txtField.getPreferredSize().height));
        txtField.setMaximumSize(new Dimension(sz, txtField.getPreferredSize().height));
        txtField.setPreferredSize(new Dimension(sz, txtField.getPreferredSize().height));

        if (type.equals(PRICE)) {
            txtField.addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    try {
                        if (getPrice() < 0) {
                            txtField.setText(txtField.getHint());
                            txtField.setForeground(Color.LIGHT_GRAY);
                        }
                    } catch (Exception x) {
                        txtField.setText(txtField.getHint());
                        txtField.setForeground(Color.LIGHT_GRAY);
                    }
                }

                public void focusGained(FocusEvent arg0) {
                }
            });
        } else {
            txtField.addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    try {
                        if (getDiscount() < 0) {
                            txtField.setText(txtField.getHint());
                            txtField.setForeground(Color.LIGHT_GRAY);
                        } else if (getDiscount() > 1) {
                            txtField.setText("100");
                        }
                    } catch (Exception x) {
                        txtField.setText(txtField.getHint());
                        txtField.setForeground(Color.LIGHT_GRAY);
                    }
                }

                public void focusGained(FocusEvent arg0) {
                }
            });
        }

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.add(Box.createHorizontalGlue());
        left.add(new JLabel(type + " for " + areaString + ": ", JLabel.TRAILING));
        left.add(Box.createHorizontalStrut(10));

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.add(txtField);
        right.add(Box.createHorizontalStrut(5));
        right.add(new JLabel(type.equals(PRICE) ? "€" : "%", JLabel.LEADING));
        right.add(Box.createHorizontalGlue());

        JPanel grid = new JPanel(new GridLayout(1, 2));
        grid.add(left);
        grid.add(right);

        this.setBorder(new EmptyBorder(5, 0, 5, 0));
        this.add(grid);
    }

    /**
     * get the price
     * 
     * @return the price
     * @throws NumberFormatException if the field couldnt be parsed
     */
    public double getPrice() throws NumberFormatException {
        return Double.parseDouble(txtField.getText());
    }

    /**
     * get the discount
     * 
     * @return the discount (between 0 and 1)
     * @throws NumberFormatException if the field couldnt be parsed
     */
    public double getDiscount() throws NumberFormatException {
        return Double.parseDouble(txtField.getText()) / 100.;
    }
}
