package view.customer.purchase;

/**
 * Enumeration of all heuristics
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public enum Heuristic {
    /** Indicates seats at centered bottom position */
    CENTERED_LOW,
    /** Indicates seats at centered middle position */
    CENTERED_MID,
    /** Indicates seats at centered upper position */
    CENTERED_HIGH,
    /** Indicates seats far from other occupied seats */
    FAR_FROM_OTHERS;
}


