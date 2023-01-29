package view.shared;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Custom class for a text field with text that helps the user to know what goes
 * in that field
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TextField extends JTextField {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Hint to be displayed */
    private String hint;

    /**
     * Constructor of the view
     * 
     * @param hint text to be displayed inside the text field when it has no text
     */
    public TextField(String hint) {
        super(hint);
        this.hint = hint;
        this.setForeground(Color.LIGHT_GRAY);
        this.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 5, 5, 5)));
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, this.getPreferredSize().height));
        this.setMinimumSize(new Dimension(this.getMinimumSize().width, this.getPreferredSize().height));
        this.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (TextField.super.getText().equals(TextField.this.hint) && TextField.this.isEditable()) {
                    TextField.this.setText("");
                    TextField.this.setForeground(Color.DARK_GRAY);
                }
            }

            public void focusLost(FocusEvent e) {
                if (TextField.this.getText().equals("")) {
                    TextField.this.clearText();
                }
            }
        });
    }

    /**
     * Getter of the text inside teh field
     * 
     * @return the text inside
     */
    @Override
    public String getText() {
        if (super.getText().equals(hint))
            return "";
        return super.getText();
    }

    /**
     * Clear the text inside the text field
     */
    public void clearText() {
        setText(hint);
        setForeground(Color.LIGHT_GRAY);
    }

    /**
     * set the width of the text field
     * 
     * @param width the width to be set
     */
    public void setWidth(int width) {
        this.setMaximumSize(new Dimension(width, this.getPreferredSize().height));
        this.setMinimumSize(new Dimension(width, this.getPreferredSize().height));
    }

    /**
     * getter for the hint attribute
     * 
     * @return the hint
     */
    public String getHint() {
        return hint;
    }
}
