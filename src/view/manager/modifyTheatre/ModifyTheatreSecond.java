package view.manager.modifyTheatre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import view.shared.SpringUtilities;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for modifying the theatre (part 2/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ModifyTheatreSecond extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Drop down menu contining the type of area to be added */
    private JComboBox<String> type;
    /** Area name textfield */
    private TextField areaName;
    /** Maximum capacity of the area */
    private TextField capacity;
    /** Price for the annual pass for specified area */
    private TextField price;

    /**
     * Constructor of the view
     */
    public ModifyTheatreSecond() {
        super("Add area", "Go back");
        this.setTitle("Add subarea");

        JPanel p = new JPanel(new SpringLayout());
        p.setBorder(new EmptyBorder(40, 20, 0, 20));

        JLabel l = new JLabel("Area type:", JLabel.TRAILING);
        type = new JComboBox<>();
        type.addItem("Composite");
        type.addItem("Non Composite");
        type.setMinimumSize(new Dimension(300, 25));
        type.setMaximumSize(new Dimension(300, 25));
        type.setPreferredSize(new Dimension(300, 25));
        l.setLabelFor(type);
        p.add(l);
        p.add(type);

        p.add(Box.createRigidArea(new Dimension(25, 25)));
        p.add(Box.createRigidArea(new Dimension(25, 25)));

        l = new JLabel("Area name:", JLabel.TRAILING);
        areaName = new TextField("Name");
        areaName.setMinimumSize(new Dimension(300, areaName.getPreferredSize().height));
        areaName.setMaximumSize(new Dimension(300, areaName.getPreferredSize().height));
        areaName.setPreferredSize(new Dimension(300, areaName.getPreferredSize().height));
        l.setLabelFor(areaName);
        p.add(l);
        p.add(areaName);

        JLabel capacityLabel = new JLabel("Area capacity:", JLabel.TRAILING);
        capacityLabel.setForeground(Color.LIGHT_GRAY);
        capacity = new TextField("5x7 or 35");
        capacity.setEditable(false);
        capacity.setMinimumSize(new Dimension(300, capacity.getPreferredSize().height));
        capacity.setMaximumSize(new Dimension(300, capacity.getPreferredSize().height));
        capacity.setPreferredSize(new Dimension(300, capacity.getPreferredSize().height));
        capacityLabel.setLabelFor(capacity);
        p.add(capacityLabel);
        p.add(capacity);
        p.add(Box.createRigidArea(new Dimension(25, 25)));

        l = new JLabel("Format: ROWSxCOLS (Sitting area) or CAPACITY (Standing area)");
        l.setFont(l.getFont().deriveFont(Font.PLAIN));
        l.setForeground(Color.GRAY);
        p.add(l);

        JLabel priceLabel = new JLabel("Annual pass price for the area:", JLabel.TRAILING);
        priceLabel.setForeground(Color.LIGHT_GRAY);
        price = new TextField("Price (€)");
        price.setEditable(false);
        price.setMinimumSize(new Dimension(300, price.getPreferredSize().height));
        price.setMaximumSize(new Dimension(300, price.getPreferredSize().height));
        price.setPreferredSize(new Dimension(300, price.getPreferredSize().height));
        priceLabel.setLabelFor(capacity);
        p.add(priceLabel);
        p.add(price);

        SpringUtilities.makeCompactGrid(p, 6, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);// padding

        type.addActionListener(e -> {
            if (type.getSelectedIndex() == 0) {
                capacityLabel.setForeground(Color.LIGHT_GRAY);
                capacity.setEditable(false);
                priceLabel.setForeground(Color.LIGHT_GRAY);
                price.setEditable(false);
            } else {
                capacityLabel.setForeground(Color.DARK_GRAY);
                capacity.setEditable(true);
                priceLabel.setForeground(Color.DARK_GRAY);
                price.setEditable(true);
            }
        });

        areaName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                if (areaName.getText().contains("(") || areaName.getText().contains(")")) {
                    areaName.setText(areaName.getHint());
                    areaName.setForeground(Color.LIGHT_GRAY);
                }
            }

        });

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.add(p);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Clears all the fields
     */
    public void emptyFields(){
        areaName.clearText();
        capacity.clearText();
        price.clearText();
    }

    /**
     * get the type of the area (Composite/NonComposite)
     * 
     * @return the area type
     */
    public String getAreaType() {
        return (String) type.getSelectedItem();
    }

    /**
     * get the area name
     * 
     * @return the name of the area
     */
    public String getAreaName() {
        return areaName.getText();
    }

    /**
     * get the area capacity. Must be interpreted by the controller (5x5 is sitting,
     * but 25 is standing)
     * 
     * @return a string with the capacity in the format of ROWSxCOLS or CAPACITY
     */
    public String getAreaCapacity() {
        return capacity.getText();
    }

    /**
     * getter for the annual pass price for the area
     * 
     * @return the price of the area
     */
    public String getPassPrice() {
        return price.getText();
    }

}
