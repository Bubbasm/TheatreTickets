package view.shared;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Template for all the views that have a title, a button for continuing and a
 * button for going back
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public abstract class TemplatePanel extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Continue button */
    private JButton continueButton;
    /** Back button */
    private JButton backButton;
    /** Title for panel */
    private JLabel title;

    /**
     * Constructor
     */
    public TemplatePanel() {
        title = new JLabel("TITLE", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, FontSize.TITLE));
        title.setBackground(this.getBackground());
        title.setBorder(new EmptyBorder(20, 20, 30, 20));

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));
        backButton = new JButton("Back to homepage");
        backButton.setFont(new Font("SansSerif", Font.BOLD, FontSize.BUTTON));

        JPanel bottomLayer = new JPanel();
        bottomLayer.setLayout(new BoxLayout(bottomLayer, BoxLayout.LINE_AXIS));
        bottomLayer.setBorder(new EmptyBorder(5, 5, 5, 5));
        bottomLayer.add(backButton);
        bottomLayer.add(Box.createGlue());
        bottomLayer.add(continueButton);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.PAGE_START);
        this.add(bottomLayer, BorderLayout.PAGE_END);
    }

    /**
     * Constructor
     * 
     * @param continueButtonName text for the continue button
     */
    public TemplatePanel(String continueButtonName) {
        this();
        this.continueButton.setText(continueButtonName);
    }

    /**
     * Constructor
     * 
     * @param continueButtonName text for the continue button
     * @param backButtonName     text for the back button
     */
    public TemplatePanel(String continueButtonName, String backButtonName) {
        this();
        this.continueButton.setText(continueButtonName);
        this.backButton.setText(backButtonName);
    }

    /**
     * Constructor
     * 
     * @param showContinue true if the continue button should appear
     */
    public TemplatePanel(boolean showContinue) {
        this();
        this.continueButton.setVisible(showContinue);
    }

    /**
     * set handler for the continue button
     * 
     * @param c action listener
     */
    public void setContinueButtonHandler(ActionListener c) {
        this.continueButton.addActionListener(c);
    }

    /**
     * Sets an action command
     * 
     * @param str the command
     */
    public void setContinueButtonActionCommand(String str) {
        this.continueButton.setActionCommand(str);
    }

    /**
     * set handler for the cancel button
     * 
     * @param c action listener
     */
    public void setBackButtonHandler(ActionListener c) {
        this.backButton.addActionListener(c);
    }

    /**
     * set the title of the view
     * 
     * @param title title of the view
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }
}
