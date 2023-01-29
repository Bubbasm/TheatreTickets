package view.shared;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * View for registering in the app
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Register extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /** Email */
    private JTextField email;
    /** Password */
    private JPasswordField password;
    /** Register button */
    private JButton register;

    /**
     * Constructor of the view
     */
    public Register() {
        JCheckBox showPassword;
        JLabel eml = new JLabel("Username: ");
        JLabel pswd = new JLabel("Password: ");
        JPanel info = new JPanel(new GridLayout(3, 2, 0, 5));
        JPanel buttons = new JPanel(new GridLayout(1, 1, 0, 0));

        email = new JTextField(20);
        password = new JPasswordField(20);
        showPassword = new JCheckBox("Show password", false);
        register = new JButton("Register");

        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);

        info.add(eml);
        info.add(email);
        info.add(pswd);
        info.add(password);
        info.add(showPassword);

        buttons.add(register);

        this.add(info);
        this.add(buttons);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, info, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, info, -buttons.getPreferredSize().height,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, buttons, 20, SpringLayout.SOUTH, info);
        buttons.setPreferredSize(new Dimension(info.getPreferredSize().width, buttons.getPreferredSize().height));
        email.addActionListener(a -> {password.requestFocus();});

        char originalHidePassword = password.getEchoChar(); // default echo char
        showPassword.addActionListener(e -> {
            password.setEchoChar(password.getEchoChar() == '\0' ? originalHidePassword : '\0');
        });
    }

    /**
     * Set handler for the register button
     * 
     * @param c action listener
     */
    public void setRegisterHandler(ActionListener c) {
        password.addActionListener(c);
        register.addActionListener(c);
    }

    /**
     * getter for the username
     * 
     * @return the username
     */
    public String getUsername() {
        return email.getText();
    }

    /**
     * getter for the password
     * 
     * @return the password
     */
    public String getPassword() {
        return new String(password.getPassword());
    }

    /**
     * Clears username and password fields
     */
    public void clearFields() {
        email.setText("");
        password.setText("");
    }

}
