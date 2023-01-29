package view.customer.search;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import view.shared.EntryList;
import view.shared.TextField;

/**
 * View representing the search bar and the results of the search
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class Search extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** search bar */
    private TextField searchBar;
    /** list of entries (results of the search) */
    private EntryList contentLayer;
    /** button to search */
    private JButton searchButton;
    /** what do we want to search */
    private SearchType searchType = SearchType.EVENT;
    /** filter for the search of performances */
    private SearchFilter searchFilter;

    /**
     * Constructor of the view
     */
    public Search() {
        contentLayer = new EntryList();

        int fontsize = 13;
        int maxWidth = 0;
        JPanel filterBar = new JPanel();
        JMenu performances = new JMenu(SearchType.PERFORMANCE.toString());
        JMenu filter = new JMenu(searchType.toString());
        JMenuBar jmb = new JMenuBar();

        SearchType[] st = { SearchType.EVENT, SearchType.CYCLE };
        SearchFilter[] sf = { SearchFilter.FUTURE, SearchFilter.MUSIC, SearchFilter.DANCE, SearchFilter.THEATRE };

        performances.setFont(new Font("SansSerif", Font.BOLD, fontsize));
        for (SearchType str : st) {
            JMenuItem jmi = new JMenuItem(str.toString());
            jmi.setFont(new Font("SansSerif", Font.BOLD, fontsize));
            if (jmi.getPreferredSize().width > maxWidth) {
                maxWidth = jmi.getPreferredSize().width;
            }
            jmi.addActionListener(e -> {
                filter.setText(str.toString());
                this.searchType = str;
                this.searchFilter = null;
            });
            filter.add(jmi);
        }
        for (SearchFilter sfr : sf) {
            JMenuItem jmi = new JMenuItem(sfr.toString());
            jmi.setFont(new Font("SansSerif", Font.BOLD, fontsize));
            if (jmi.getPreferredSize().width > maxWidth) {
                maxWidth = jmi.getPreferredSize().width;
            }
            jmi.addActionListener(e -> {
                filter.setText(sfr.toString());
                this.searchType = SearchType.PERFORMANCE;
                this.searchFilter = sfr;
            });
            performances.add(jmi);
        }

        filter.setFont(new Font("SansSerif", Font.BOLD, fontsize));
        filter.add(performances);
        filter.setPreferredSize(new Dimension(maxWidth, filter.getPreferredSize().height));
        jmb.add(filter);
        jmb.setFont(new Font("SansSerif", Font.BOLD, fontsize));

        JLabel icon = new JLabel(new ImageIcon("./media/filter.png", "Filter"));
        icon.setBackground(this.getBackground());
        filterBar.add(icon);
        filterBar.add(jmb);

        searchBar = new TextField("Search for anything");
        searchBar.setBackground(this.getBackground());
        searchBar.setFont(new Font("SansSerif", Font.BOLD, 20));
        searchBar.setBorder(new EmptyBorder(7, 5, 0, 5));

        searchButton = new JButton(new ImageIcon("./media/search.png", "Search"));

        JPanel searchLayer = new JPanel(new BorderLayout());

        searchButton.setPressedIcon(new ImageIcon("./media/searchPress.png", "SearchPress"));
        searchButton.setOpaque(false);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setPreferredSize(new Dimension(25, 20));

        searchLayer.add(searchButton, BorderLayout.LINE_START);
        searchLayer.add(searchBar, BorderLayout.CENTER);
        searchLayer.add(filterBar, BorderLayout.LINE_END);

        this.setLayout(new BorderLayout());

        this.add(searchLayer, BorderLayout.PAGE_START);
        this.add(contentLayer, BorderLayout.CENTER);
    }

    /**
     * Constructor of the view
     * 
     * @param entries entries to be added to the content panel
     */
    public Search(SearchEntry... entries) {
        this();
        for (SearchEntry p : entries) {
            this.addSearchEntry(p);
        }
    }

    /**
     * clear the text inside the search bar
     */
    public void clearSearch() {
        searchBar.clearText();
    }

    /**
     * Set the handler of the search button
     * 
     * @param c action listener
     */
    public void setSearchHandler(ActionListener c) {
        searchButton.addActionListener(c);
        searchBar.addActionListener(c);
    }

    /**
     * Add an entry to the content panel
     * 
     * @param panel Entry to add
     */
    public void addSearchEntry(SearchEntry panel) {
        contentLayer.addEntry(panel);
    }

    /**
     * clear all the entries inside the content panel
     */
    public void emptySearch() {
        contentLayer.emptyEntries();
    }

    /**
     * Get the type of search that we want to make
     * 
     * @return the search type
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * Get the filter of the search
     * 
     * @return the filter
     */
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    /**
     * Get the string that we want to search
     * 
     * @return the text in the search bar
     */
    public String getSearch() {
        return searchBar.getText();
    }

}
