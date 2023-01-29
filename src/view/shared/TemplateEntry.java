package view.shared;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Template for all the entries that go inside an entry list
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class TemplateEntry extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param heigth height of the entry
     */
    public TemplateEntry(int heigth) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.setPreferredSize(new Dimension(this.getPreferredSize().width, heigth));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, heigth));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, heigth));

        this.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2),
                new CompoundBorder(new LineBorder(Color.GRAY, 1), new EmptyBorder(0, 5, 0, 5))));
    }
}
