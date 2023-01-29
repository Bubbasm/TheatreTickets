package model. enums;

/**
 * Enumeration for the status of the reservations
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public enum ReservationStatus {
    /** Status of a reservation that hasn't been confirmed/cancelled/expired */
    ACTIVE,
    /** Status of a reservation that has been confirmed */
    CONFIRMED,
    /** Status of a reservation that has been cancelled */
    CANCELLED,
    /** Status of a reservation that has expired */
    EXPIRED;
}
