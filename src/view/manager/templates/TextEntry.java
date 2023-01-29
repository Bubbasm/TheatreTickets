package view.manager.templates;

import java.awt.*;

import javax.swing.*;

import view.shared.FontSize;
import view.shared.TemplateEntry;

/**
 * Entry which only contains text (used in statistics)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TextEntry extends TemplateEntry {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param txt Text to display
     */
    public TextEntry(String txt) {
        super(50);
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.PLAIN, FontSize.SUBTITLE));
        this.add(Box.createHorizontalGlue());
        this.add(l);
        this.add(Box.createHorizontalGlue());
    }
}
