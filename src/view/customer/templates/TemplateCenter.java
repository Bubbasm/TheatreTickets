package view.customer.templates;

import java.awt.*;

import view.shared.EntryList;
import view.shared.TemplatePanel;
import view.shared.TemplateEntry;

/**
 * Template for the views that show your notifications/reservations/etc
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class TemplateCenter extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** entry list with all the entries (e.g. notifications) */
    private EntryList entries;

    /**
     * Constructor
     * 
     * @param title title of the view
     */
    public TemplateCenter(String title) {
        super(false);
        entries = new EntryList();

        this.add(entries, BorderLayout.CENTER);
        this.setTitle(title);

    }

    /**
     * Constructor
     * 
     * @param title   title of the view
     * @param entries entries to add
     */
    public TemplateCenter(String title, TemplateEntry... entries) {
        this(title);
        for (TemplateEntry p : entries) {
            this.addEntry(p);
        }
    }

    /**
     * add an entry to the entry list
     * 
     * @param panel entry to add
     */
    public void addEntry(TemplateEntry panel) {
        entries.addEntry(panel);
    }

    /**
     * clear all the entries
     */
    public void emptyEntries() {
        entries.emptyEntries();
    }

}
