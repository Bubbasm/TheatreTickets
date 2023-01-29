package view.customer.search;

/**
 * View representing an entry in the list of performances
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PerformanceEntry extends SearchEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the view
     * 
     * @param title         title of the event
     * @param date          date of the performance
     * @param isSoldOut     true if the performance is sold out
     * @param isCancelled   true is performance is cancelled
     * @param showEventName true if the name of the event must appear
     */
    public PerformanceEntry(String title, String date, boolean isSoldOut, boolean isCancelled, boolean showEventName) {
        super();
        this.text.setText((showEventName ? title + " at " : "") + date);
        if (isSoldOut) {
            this.text.setText(this.text.getText() + " SOLD OUT!");
            this.button.setText("Enter waiting list");
        } else {
            this.button.setText("See availability");
        }
        this.text.setText(this.text.getText() + (isCancelled ? "     CANCELLED" : ""));
    }

}
