package view.customer.purchase;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import view.shared.FontSize;
import view.shared.TemplatePanel;
import view.shared.TreePanel;

/**
 * View that allows selecting an area for purchase
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AreaSelection extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Tree panel containing the main information */
    private TreePanel contentPanel;
    /** Button indicating if reservation is requested */
    private JRadioButton reserve;

    /**
     * Constructor
     * 
     * @param tree Tree representing all information
     */
    public AreaSelection(JTree tree) {
        this.setTitle("Please select an area for your tickets");
        contentPanel = new TreePanel(tree);
        JPanel outter = new JPanel(new BorderLayout());

        JPanel radio = new JPanel();
        radio.setLayout(new BoxLayout(radio, BoxLayout.X_AXIS));
        radio.setBorder(new EmptyBorder(0,0,30,0));

        JRadioButton purchase = new JRadioButton("Ready to pay", true);
        purchase.setFont(new Font("SansSerif", Font.PLAIN, FontSize.BODY));
        reserve = new JRadioButton("Rather reserve");
        reserve.setFont(new Font("SansSerif", Font.PLAIN, FontSize.BODY));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(purchase);
        buttonGroup.add(reserve);
        radio.add(Box.createGlue());
        radio.add(purchase);
        radio.add(reserve);
        radio.add(Box.createGlue());

        outter.add(contentPanel, BorderLayout.CENTER);
        outter.add(radio, BorderLayout.SOUTH);

        this.add(outter, BorderLayout.CENTER);
    }

    /**
     * Disables reservation postibility
     */
    public void disableReservation() {
        reserve.setEnabled(false);
    }

    /**
     * Getter for current selection
     * 
     * @return String representing the area selected
     */
    public String getSelection() {
        return this.contentPanel.getSelection();
    }

    /**
     * @return True if is to be reserved
     */
    public boolean isToBeReserved() {
        return reserve.isSelected();
    }
}
