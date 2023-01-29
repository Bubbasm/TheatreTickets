package view.shared;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

/**
 * Template for an entry list (used in search for example)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EntryList extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Indicates vertical alignment of entries */
    public static final int VERTICAL = BoxLayout.PAGE_AXIS;
    /** Indicates horizontal alignment of entries */
    public static final int HORIZONTAL = BoxLayout.LINE_AXIS;
    /** Inner panel containing entries */
    private JPanel innerPanel;
    /** Scroll pane */
    private JScrollPane contentLayer;

    /**
     * Constructor of the view
     * 
     * @param entryAlignment Vertical/horizontal
     */
    public EntryList(int entryAlignment) {
        innerPanel = new JPanel();
        contentLayer = new JScrollPane(innerPanel) {
            /** Serialized class */
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize() {
                Dimension dim;
                if (entryAlignment == VERTICAL) {
                    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    dim = new Dimension(super.getPreferredSize().width + getVerticalScrollBar().getSize().width,
                            super.getPreferredSize().height);
                    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                } else {

                    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    dim = new Dimension(super.getPreferredSize().width,
                            super.getPreferredSize().height + getHorizontalScrollBar().getSize().height);
                    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                }
                return dim;
            }
        };
        contentLayer.setBorder(new LineBorder(Color.GRAY.brighter()));

        innerPanel.setLayout(new BoxLayout(innerPanel, entryAlignment));

        this.setLayout(new GridLayout(1, 1));
        this.add(contentLayer);
    }

    /**
     * Constructor of the view
     */
    public EntryList() {
        this(VERTICAL);
    }

    /**
     * Add an entry to the panel
     * 
     * @param panel Entry to add
     */
    public void addEntry(TemplateEntry panel) {
        innerPanel.add(panel);
        innerPanel.revalidate();
    }

    /**
     * remove all the entries inside this
     */
    public void emptyEntries() {
        innerPanel.removeAll();
        innerPanel.revalidate();
        innerPanel.repaint();
    }

    /**
     * Remove the border of the scroll pane
     */
    public void removeScrollBorder() {
        contentLayer.setBorder(new EmptyBorder(0, 0, 0, 0));
    }
}
