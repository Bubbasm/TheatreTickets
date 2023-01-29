package view.customer.search;

/**
 * View representing an entry in the list of events
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EventEntry extends SearchEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the view
     * 
     * @param title  name of the event
     * @param author author of the event
     */
    public EventEntry(String title, String author) {
        this(title, author, null);
    }

    /**
     * Constructor of the view
     * 
     * @param title       title of the event
     * @param author      author of the event
     * @param description description of the event
     */
    public EventEntry(String title, String author, String description) {
        super();
        this.text.setText(title + " by " + author + (description != null ? ". Brief: " + description : ""));
        this.button.setText("More details");
    }
}
