package model. exceptions;

/**
 * Exception thrown when handling operations (purchases and reservtions)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class OperationException extends Exception {

    /** Default seialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * General constructor of an exeption
     * 
     * @param message Message to be throwed
     */
    public OperationException(String message) {
        super(message);
    }
}
