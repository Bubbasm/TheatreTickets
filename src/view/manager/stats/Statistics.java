package view.manager.stats;

import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import view.manager.enums.StatisticsFilter;
import view.manager.enums.StatisticsType;
import view.manager.templates.TextEntry;
import view.shared.EntryList;
import view.shared.FontSize;
import view.shared.TemplatePanel;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * View for the statistics
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Statistics extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** button to get the statstics */
    private JButton getStatsBut;
    /** filter selector (higher/lower) */
    private JComboBox<String> filterSelector;
    /** type selector (revenue/attendance) */
    private JComboBox<String> typeSelector;
    /** event selector */
    private JComboBox<String> eventSelector;
    /** performance selector */
    private JComboBox<String> perfSelector;
    /** entry list for the statistics */
    private EntryList bottom;

    /**
     * Constructor of the view
     * 
     * @param events  list of the events
     * @param perfMap map that associates each event with its performances
     */
    public Statistics(List<String> events, Map<String, List<String>> perfMap) {
        super(false);
        this.setTitle("Statistics");
        bottom = new EntryList();
        bottom.removeScrollBorder();
        filterSelector = new JComboBox<>();
        typeSelector = new JComboBox<>();
        eventSelector = new JComboBox<>();
        perfSelector = new JComboBox<>();
        eventSelector.addActionListener(e -> {
            updatePerf(perfMap.get(getEventName()));
        });
        update(events, perfMap);

        filterSelector.setMaximumSize(new Dimension(100, typeSelector.getPreferredSize().height));
        filterSelector.setMinimumSize(new Dimension(100, typeSelector.getPreferredSize().height));
        filterSelector.setPreferredSize(new Dimension(100, typeSelector.getPreferredSize().height));

        typeSelector.setMaximumSize(new Dimension(100, typeSelector.getPreferredSize().height));
        typeSelector.setMinimumSize(new Dimension(100, typeSelector.getPreferredSize().height));
        typeSelector.setPreferredSize(new Dimension(100, typeSelector.getPreferredSize().height));

        eventSelector.setMaximumSize(new Dimension(200, eventSelector.getPreferredSize().height));
        eventSelector.setMinimumSize(new Dimension(200, eventSelector.getPreferredSize().height));
        eventSelector.setPreferredSize(new Dimension(200, eventSelector.getPreferredSize().height));

        perfSelector.setMaximumSize(new Dimension(200, perfSelector.getPreferredSize().height));
        perfSelector.setMinimumSize(new Dimension(200, perfSelector.getPreferredSize().height));
        perfSelector.setPreferredSize(new Dimension(200, perfSelector.getPreferredSize().height));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel l = new JLabel("Event: ");
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        l.setLabelFor(eventSelector);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(eventSelector);

        l = new JLabel("Performance: ");
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        l.setLabelFor(perfSelector);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(perfSelector);

        l = new JLabel("Show: ");
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        l.setLabelFor(typeSelector);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        typeSelector.addItem(StatisticsType.REVENUE.str);
        typeSelector.addItem(StatisticsType.ATTENDANCE.str);
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(typeSelector);

        l = new JLabel("Filter: ");
        l.setFont(new Font("SansSerif", Font.BOLD, FontSize.BODY));
        l.setLabelFor(filterSelector);
        l.setBorder(new EmptyBorder(0, 0, 0, 10));
        filterSelector.addItem(StatisticsFilter.HIGHER_FIRST.str);
        filterSelector.addItem(StatisticsFilter.LOWER_FIRST.str);
        top.add(Box.createHorizontalGlue());
        top.add(l);
        top.add(filterSelector);

        getStatsBut = new JButton("Get statistics");
        getStatsBut.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));
        top.add(Box.createHorizontalGlue());
        top.add(getStatsBut);
        top.add(Box.createHorizontalGlue());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(0, 50, 20, 50));
        center.add(top);
        center.add(bottom);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * method to update the view (used in the controller)
     * 
     * @param events  list of the events
     * @param perfMap map that associates each event with its performances
     */
    public void update(List<String> events, Map<String, List<String>> perfMap) {
        eventSelector.removeAllItems();
        for (String s : events) {
            eventSelector.addItem(s);
        }
        updatePerf(perfMap.get(events.get(0)));
        bottom.emptyEntries();
    }

    /**
     * Update the performance selector
     * 
     * @param list List of performances to show
     */
    private void updatePerf(List<String> list) {
        perfSelector.removeAllItems();
        for (String p : list) {
            perfSelector.addItem(p);
        }
    }

    /**
     * update the statistics area
     * 
     * @param areaStats Statistics results
     */
    public void updateStats(List<String> areaStats) {
        bottom.emptyEntries();
        for (String s : areaStats) {
            bottom.addEntry(new TextEntry(s));
        }
    }

    /**
     * get the event name
     * 
     * @return the event name
     */
    public String getEventName() {
        return (String) eventSelector.getSelectedItem();
    }

    /**
     * get the performance name
     * 
     * @return the performance name
     */
    public LocalDateTime getPerformanceDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return LocalDateTime.parse((String) perfSelector.getSelectedItem(), formatter);
    }

    /**
     * get the type (attencance/revenue)
     * 
     * @return the type
     */
    public StatisticsType getType() {
        if (((String) typeSelector.getSelectedItem()).equals(StatisticsType.ATTENDANCE.str)) {
            return StatisticsType.ATTENDANCE;
        } else {
            return StatisticsType.REVENUE;
        }
    }

    /**
     * get the filter (higher/lower)
     * 
     * @return the filter
     */
    public StatisticsFilter getFilter() {
        if (((String) filterSelector.getSelectedItem()).equals(StatisticsFilter.HIGHER_FIRST.str)) {
            return StatisticsFilter.HIGHER_FIRST;
        } else {
            return StatisticsFilter.LOWER_FIRST;
        }
    }

    /**
     * set the handler for the statistics button
     * 
     * @param a Action listener
     */
    public void setStatsButtonHandler(ActionListener a) {
        getStatsBut.addActionListener(a);
    }

}
