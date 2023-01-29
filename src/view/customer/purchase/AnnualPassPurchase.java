package view.customer.purchase;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import view.shared.TemplatePanel;
import view.shared.TextField;
import view.shared.TreePanel;

/**
 * View representing the purchase of an annual pass
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AnnualPassPurchase extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** The main content panel */
    private TreePanel contentPanel;
    /** Credit card number */
    private JTextField ccNum;
    /** Quantity to pay with credit card */
    private double qty = 0.0;

    /**
     * Constructor of the view
     * 
     * @param tree Tree containing all information to be represented
     */
    public AnnualPassPurchase(JTree tree) {
        super("Pay now", "Go back");
        this.setTitle("Please select an area for the pass");
        contentPanel = new TreePanel(tree);

        JLabel txt = new JLabel();
        txt.setVisible(false);
        txt.setFont(new Font("SansSerif", Font.BOLD, 15));

        ccNum = new TextField("Introduce your Credit Card number");
        ccNum.setFont(txt.getFont().deriveFont(Font.PLAIN));
        ccNum.setAlignmentX(Component.LEFT_ALIGNMENT);
        ccNum.setMaximumSize(new Dimension(300, ccNum.getPreferredSize().height));
        ccNum.setMinimumSize(new Dimension(300, ccNum.getPreferredSize().height));

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node.isLeaf()) {
                String data = (String) node.getUserObject();
                int start = data.indexOf("price: ") + 7;
                int end = data.indexOf(" €");
                String s = data.substring(start, end);
                qty = Double.parseDouble(s);
                txt.setText(qty + "€ will be charged");
                txt.setVisible(true);
            }
        });

        JPanel ccPanel = new JPanel();
        ccPanel.setLayout(new BoxLayout(ccPanel, BoxLayout.LINE_AXIS));
        ccPanel.add(ccNum);
        ccPanel.add(Box.createHorizontalStrut(20));
        ccPanel.add(txt);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(contentPanel);
        center.add(ccPanel);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Getter for current area selected
     * 
     * @return String representing the area selected
     */
    public String getSelection() {
        return this.contentPanel.getSelection();
    }

    /**
     * Getter for quantity
     * 
     * @return Quantity
     */
    public double getQuantityToCharge() {
        return qty;
    }

    /**
     * Getter for credit card number
     * 
     * @return The number
     */
    public String getCreditCardNumber() {
        return ccNum.getText();
    }
}
