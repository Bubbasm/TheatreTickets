package view.manager.addEvent;

import java.util.*;
import java.util.List;

import java.awt.*;

import view.manager.templates.AreaEntry;
import view.shared.EntryList;
import view.shared.TemplatePanel;

/**
 * View for adding a new event (part 2/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddEventSecond extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Map to associate area entries with their given name */
    private Map<String, AreaEntry> entries;
    /** Panel to put all text entries */
    private EntryList panel;

    /**
     * Constructor of the view
     */
    public AddEventSecond() {
        super("Add Event");
        this.setTitle("Add Event (2/2)");

        entries = new HashMap<>();
        panel = new EntryList();
        panel.removeScrollBorder();

        this.add(panel, BorderLayout.CENTER);
    }

    /**
     * Sets the area names and creates text fields to store prices
     *
     * @param areaList List of area names
     */
    public void update(List<String> areaList) {
        panel.emptyEntries();
        for (String area : areaList) {
            AreaEntry ae = new AreaEntry(area, AreaEntry.PRICE);
            entries.put(area, ae);
            panel.addEntry(ae);
        }
    }

    /**
     * Getter for the prices for each area
     * 
     * @return a map that associates each area with its price
     * @throws NumberFormatException when the string in the text fields couldn be
     *                               parsed (empty, invalid characters, etc)
     */
    public Map<String, Double> getPrices() throws NumberFormatException {
        Map<String, Double> res = new HashMap<>();
        for (String area : entries.keySet()) {
            double price = entries.get(area).getPrice();
            res.put(area.split(" \\(")[0], price);
        }
        return res;
    }

}
