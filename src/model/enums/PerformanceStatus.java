package model. enums;

/**
 * Enumeration for the status of a performance
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public enum PerformanceStatus {
    /** Active status */
    ACTIVE,
    /** Indicates that the performance has been postponed */
    POSTPONED,
    /** Indicates that the performance has been cancelled */
    CANCELLED,
    /** Indicates that the performance has already occurred */
    EXPIRED;
}
