package view.manager.disableSeats;

import javax.swing.*;

import view.shared.TemplatePanel;
import view.shared.TreePanel;

import java.awt.*;

/**
 * View for disabling seats (part 1/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class DisableSeatsFirst extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Panel containing the area tree */
    private TreePanel contentPanel;

    /**
     * Constructor of the view
     * 
     * @param tree tree with the areas to be displayed
     */
    public DisableSeatsFirst(JTree tree) {
        this.setTitle("Disable Seats (1/2)");
        contentPanel = new TreePanel(tree);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * getter of the selected area
     * 
     * @return the area name
     */
    public String getSelection() {
        return this.contentPanel.getSelection();
    }

}
