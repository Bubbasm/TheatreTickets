package view.manager.modifyTheatre;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import view.shared.TemplatePanel;
import view.shared.TreePanel;

/**
 * View for modifying the theatre (part 1/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class ModifyTheatreFirst extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Panel with the tree of area organization */
    private TreePanel topPanel;
    /** Button to add area */
    private JButton addArea;

    /**
     * Constructor of the view
     * 
     * @param tree tree with the areas to be displayed
     */
    public ModifyTheatreFirst(JTree tree) {
        super(false);
        this.setTitle("Add Area");
        topPanel = new TreePanel(tree, true, true);

        addArea = new JButton("Add subarea to the selection");

        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.X_AXIS));
        botPanel.add(Box.createHorizontalGlue());
        botPanel.add(addArea);
        botPanel.add(Box.createHorizontalGlue());

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(0, 0, 40, 0));
        center.add(topPanel, BorderLayout.CENTER);
        center.add(botPanel, BorderLayout.PAGE_END);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Getter for the selected area
     * 
     * @return the area name
     */
    public String getSelection() {
        return this.topPanel.getSelection();
    }

    /**
     * set the handler of the add area button
     * 
     * @param l action listener
     */
    public void setAddAreaHandler(ActionListener l) {
        this.addArea.addActionListener(l);
    }

}
