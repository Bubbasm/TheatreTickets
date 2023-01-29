package model. areas;

/**
 * Abstract class for the Non composite areas. Child of the class Area.
 *
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public abstract class NonComposite extends Area {
    /** Default UID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param name name of the area
     */
    public NonComposite(String name) {
        super(name);
    }
}
