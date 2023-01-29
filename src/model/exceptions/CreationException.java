package model. exceptions;

/**
 * Exception thrown in some constructors
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class CreationException extends Exception {

    /** Default seialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * General constructor of an exeption
     * 
     * @param message Message to be throwed
     */
    public CreationException(String message) {
        super(message);
    }
}
