package model.enums;

/**
 * Enumeration for the filters that can be applied to a search
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public enum SearchFilter {
    /** Search only the performances of events of type dance */
    DANCE,
    /** Search only the performances of events of type theatre */
    THEATRE,
    /** Search only the performances of events of type music */
    MUSIC,
    /** Search only the performances that have not occurred yet */
    FUTURE;
}
