package view.customer.purchase;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import view.shared.TemplatePanel;
import view.shared.TextField;
import view.shared.TreePanel;

/**
 * View to purchase a cycle pass
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CyclePassPurchase extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Cycle selected */
    private JComboBox<String> cycleSelector;
    /** Pannel containing the tree */
    private TreePanel contentPanel;
    /** Credit card number */
    private JTextField ccNum;
    /** Quantity to be charged */
    private double qty = 0.0;
    /** Top panel to be updated */
    private JPanel top;
    /** Text label to be updated */
    private JLabel txt;

    /**
     * Constructor
     * 
     * @param tree Tree representing information of areas
     * @param map  Map relating cycles with its events
     */
    public CyclePassPurchase(JTree tree, Map<String, String> map) {
        super("Pay now", "Go back");
        this.setTitle("Please select an area for the pass");

        top = new JPanel();
        top.setLayout(new GridLayout(1, 1));
        updateTree(tree);

        txt = new JLabel();
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
                txt.setText(qty + " € will be charged");
                txt.setVisible(true);
            }
        });

        JPanel ccPanel = new JPanel();
        ccPanel.setLayout(new BoxLayout(ccPanel, BoxLayout.LINE_AXIS));
        ccPanel.add(ccNum);
        ccPanel.add(Box.createHorizontalStrut(20));
        ccPanel.add(txt);

        cycleSelector = new JComboBox<>();
        for (String s : map.keySet()) {
            cycleSelector.addItem(s);
        }
        cycleSelector.setMaximumSize(new Dimension(200, cycleSelector.getPreferredSize().height));
        cycleSelector.setBorder(new EmptyBorder(0, 20, 0, 0));
        cycleSelector.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("Events contained in this cycle:");
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(0, 20, 0, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label2 = new JLabel("Select a cycle:");
        label2.setFont(new Font("SansSerif", Font.BOLD, 15));
        label2.setBorder(new EmptyBorder(0, 20, 10, 0));
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea eventsContained = new JTextArea();
        eventsContained.setText(map.get(cycleSelector.getSelectedItem()));
        eventsContained.setEditable(false);
        eventsContained.setBackground(this.getBackground());
        eventsContained.setFont(label.getFont().deriveFont(Font.PLAIN));
        eventsContained.setBorder(new EmptyBorder(20, 20, 20, 20));
        eventsContained.setAlignmentX(Component.LEFT_ALIGNMENT);

        cycleSelector.addActionListener(e -> {
            eventsContained.setText(map.get(cycleSelector.getSelectedItem()));
            txt.setText("");
            txt.setVisible(false);
        });

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(label2);
        leftPanel.add(cycleSelector);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(label);
        leftPanel.add(eventsContained);
        leftPanel.add(Box.createVerticalGlue());
        // leftPanel.setBorder(new EmptyBorder(0,0,0,40));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(top);
        center.add(ccPanel);

        this.add(center, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.LINE_START);
    }

    /**
     * Getter for the area selected
     * 
     * @return String representing the area selected
     */
    public String getAreaSelected() {
        return this.contentPanel.getSelection();
    }

    /**
     * Getter for the cycle selected
     * 
     * @return The cycle
     */
    public String getCycleSelected() {
        return (String) this.cycleSelector.getSelectedItem();
    }

    /**
     * Getter for credit card number
     * 
     * @return Credit card number
     */
    public String getCreditCardNumber() {
        return ccNum.getText();
    }

    /**
     * Sets the action listener for the cycle selector
     * 
     * @param a Action listener
     */
    public void setCycleSelectorHandler(ActionListener a) {
        this.cycleSelector.addActionListener(a);
    }

    /**
     * Updates the tree with a new JTree
     * 
     * @param tree New JTree
     */
    public void updateTree(JTree tree) {
        if (top != null && contentPanel != null)
            top.remove(contentPanel);
        contentPanel = new TreePanel(tree);
        contentPanel.setBorder(new EmptyBorder(0, 60, 0, 0));
        top.add(contentPanel);

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node.isLeaf()) {
                String data = (String) node.getUserObject();
                int start = data.indexOf("price: ") + 7;
                int end = data.indexOf(" €");
                String s = data.substring(start, end);
                qty = Double.parseDouble(s);
                txt.setText(qty + " € will be charged");
                txt.setVisible(true);
            }
        });
    }

}
