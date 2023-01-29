package view.customer.search;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import view.shared.TemplatePanel;
import view.shared.EntryList;
import view.shared.FontSize;

/**
 * View that shows more information of a cycle
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CycleEntryDetailed extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** list that shows every event in the cycle */
    private EntryList contentLayer;

    /**
     * Constructor of the view
     * 
     * @param title name of the cycle
     */
    public CycleEntryDetailed(String title) {
        super(false);
        JPanel outterPanel = new JPanel();
        outterPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("List of events contained in this cycle");
        label.setFont(new Font("SansSerif", Font.PLAIN, FontSize.SUBTITLE));
        label.setBorder(new EmptyBorder(5, 5, 5, 5));

        contentLayer = new EntryList();

        outterPanel.add(label, BorderLayout.PAGE_START);
        outterPanel.add(contentLayer, BorderLayout.CENTER);

        this.add(outterPanel);
        this.setTitle(title);

    }

    /**
     * Adds a new event entry to the list
     * 
     * @param ee event entry to add
     */
    public void addEventEntry(EventEntry ee) {
        contentLayer.addEntry(ee);
    }
}
