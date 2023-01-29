package view.manager.enums;

/**
 * Enumeration which represents the type of statistic that we want to see
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public enum StatisticsType {
    /** see the attendance */
    ATTENDANCE("Attendance"),
    /** see the revenue */
    REVENUE("Revenue");

    /** string representation */
    public String str;
    /**
     * Private constructor
     * @param s string representation
     */
    private StatisticsType(String s){
        this.str = s;
    }
}
