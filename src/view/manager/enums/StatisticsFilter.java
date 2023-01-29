package view.manager.enums;

/**
 * Enumeration which represents the filters for the stats
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public enum StatisticsFilter {
    /** Show higher results first */
    HIGHER_FIRST("Higher first"),
    /** Show lower results first */
    LOWER_FIRST("Lower first");

    /** string representation */
    public String str;

    /**
     * private constructor
     * @param s string representation
     */
    private StatisticsFilter(String s){
        this.str = s;
    }
}
