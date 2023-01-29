package view.customer.search;

/**
 * View representing an entry in the list of cycles
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CycleEntry extends SearchEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the view
     * 
     * @param name name of the cycle
     */
    public CycleEntry(String name) {
        super();
        this.text.setText(name);
        this.button.setText("More details");
    }
}
