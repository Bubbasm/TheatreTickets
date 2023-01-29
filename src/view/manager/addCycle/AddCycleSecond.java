package view.manager.addCycle;

import java.util.*;
import java.util.List;

import java.awt.*;

import view.manager.templates.AreaEntry;
import view.shared.EntryList;
import view.shared.TemplatePanel;

/**
 * View for adding a new cycle (part 2/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddCycleSecond extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Map which associates each area with its entry in the list */
    private Map<String, AreaEntry> entries;

    /**
     * Constructor of the view
     * 
     * @param areaList list with the names of the areas
     */
    public AddCycleSecond(List<String> areaList) {
        super("Add Cycle", "Back");
        this.setTitle("Add Cycle (2/2)");

        entries = new HashMap<>();
        EntryList panel = new EntryList();
        panel.removeScrollBorder();

        for (String area : areaList) {
            AreaEntry ae = new AreaEntry(area, AreaEntry.DISCOUNT);
            entries.put(area.split(" \\(")[0], ae);
            panel.addEntry(ae);
        }
        this.add(panel, BorderLayout.CENTER);
    }

    /**
     * Getter for the discounts
     * 
     * @return a map that associates each area with the discount that will be
     *         applied to it for this cycle
     * @throws NumberFormatException when the text in the fields cannot be parsed
     *                               (empty, incorrect characters, etc)
     */
    public Map<String, Double> getDiscounts() throws NumberFormatException {
        Map<String, Double> res = new HashMap<>();
        for (String area : entries.keySet()) {
            double disc = entries.get(area).getDiscount();
            res.put(area, disc);
        }
        return res;
    }
}
