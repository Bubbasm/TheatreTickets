package model. exceptions;

/**
 * exception to handle invalid logins (incorrect user/password)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class InvalidLoginException extends Exception {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param msg Error message
     */
    public InvalidLoginException(String msg) {
        super(msg);
    }
}
