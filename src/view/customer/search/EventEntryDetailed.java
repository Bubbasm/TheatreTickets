package view.customer.search;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import view.shared.TemplatePanel;
import view.shared.EntryList;
import view.shared.FontSize;

/**
 * View that shows more information about an event
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class EventEntryDetailed extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** constant for the width of the image */
    private static final int imageWidth = 256;
    /** constant for the height of the image */
    private static final int imageHeight = 256;
    /** list of performances in this event */
    private EntryList contentLayer;
    /** description of the event */
    private JTextArea descrip;

    /**
     * Constructor of the view
     * 
     * @param iconPath    path to the image
     * @param title       title of the event
     * @param description description of the event
     */
    public EventEntryDetailed(String iconPath, String title, String description) {
        super(false);
        JPanel outterPanel = new JPanel();
        outterPanel.setLayout(new GridLayout(2, 1));

        JLabel image = new JLabel();
        ImageIcon ii;
        if (iconPath != null && !iconPath.equals("")) {
            ii = new ImageIcon(iconPath);
        } else {
            ii = new ImageIcon("./media/EventImage.png");
        }
        ii.setImage(ii.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));
        image.setIcon(ii);

        contentLayer = new EntryList();

        JPanel upper = new JPanel();
        upper.setLayout(new BoxLayout(upper, BoxLayout.LINE_AXIS));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.PAGE_AXIS));

        descrip = new JTextArea();

        descrip.setFont(new Font("SansSerif", Font.PLAIN, FontSize.BODY));
        descrip.setEditable(false);
        descrip.setBackground(this.getBackground());
        JScrollPane jsp = new JScrollPane(descrip);
        jsp.setBorder(new EmptyBorder(0, 0, 0, 0));

        descrip.setText(description);
        text.add(jsp);
        text.add(Box.createGlue());
        text.setBorder(new EmptyBorder(10, 10, 10, 10));

        upper.add(image);
        upper.add(text);
        upper.setBorder(new EmptyBorder(10, 20, 10, 20));

        outterPanel.add(upper);
        outterPanel.add(contentLayer);
        this.add(outterPanel, BorderLayout.CENTER);
        this.setTitle(title);
    }

    /**
     * Add a new entry to the list of performances
     * 
     * @param pe entry to be added
     */
    public void addPerformanceEntry(PerformanceEntry pe) {
        contentLayer.addEntry(pe);
    }
}
