package model. exceptions;

/**
 * Exception that handles bad names when registering (names already taken)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class InvalidUsernameException extends Exception {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param msg Error message
     */
    public InvalidUsernameException(String msg) {
        super(msg);
    }
}
