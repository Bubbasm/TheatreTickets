package view.shared;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * View for login/register
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class LogInSignUp extends JPanel {

    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /** username field */
    private JTextField username;
    /** password field */
    private JPasswordField password;
    /** checkbox for showing or not the password */
    private JCheckBox showPassword;
    /** button to login */
    private JButton login;
    /** button to register */
    private JButton signup;
    /** button to skip the login */
    private JButton skip;
    /** character used to hide the password */
    private char originalHidePassword;

    /**
     * Constructor of the view
     */
    public LogInSignUp() {
        JLabel eml = new JLabel("Username: ");
        JLabel pswd = new JLabel("Password: ");
        JPanel logAndSignIn = new JPanel(new GridLayout(1, 2, 5, 0));
        JPanel info = new JPanel(new GridLayout(3, 2, 0, 5));
        JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));

        username = new JTextField(20);
        password = new JPasswordField(20);
        showPassword = new JCheckBox("Show password", false);
        login = new JButton("Log In");
        signup = new JButton("Register");
        skip = new JButton("Skip Log In");

        SpringLayout layout = new SpringLayout();

        this.setLayout(layout);

        info.add(eml);
        info.add(username);
        info.add(pswd);
        info.add(password);
        info.add(showPassword);

        logAndSignIn.add(login);
        logAndSignIn.add(signup);
        buttons.add(logAndSignIn);
        buttons.add(skip);

        this.add(info);
        this.add(buttons);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, info, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, info, -buttons.getPreferredSize().height,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, buttons, 20, SpringLayout.SOUTH, info);
        buttons.setPreferredSize(new Dimension(info.getPreferredSize().width, buttons.getPreferredSize().height));
        username.addActionListener(e -> {
            password.requestFocus();
        });

        originalHidePassword = password.getEchoChar(); // default echo char
        showPassword.addActionListener(e -> {
            password.setEchoChar(password.getEchoChar() == 0 ? originalHidePassword : '\0');
        });
    }

    /**
     * set the handler for the login button
     * 
     * @param c action listener
     */
    public void setLoginHandler(ActionListener c) {
        password.addActionListener(c);
        login.addActionListener(c);
    }

    /**
     * set the handler for the sign up button
     * 
     * @param c action listener
     */
    public void setSignUpHandler(ActionListener c) {
        signup.addActionListener(c);
    }

    /**
     * set the handler for the sip login button
     * 
     * @param c action listener
     */
    public void setSkipHandler(ActionListener c) {
        skip.addActionListener(c);
    }

    /**
     * Clear all the fields
     */
    public void clearFields() {
        username.setText("");
        password.setText("");
        password.setEchoChar(originalHidePassword);
        showPassword.setSelected(false);

    }

    /**
     * Get the username
     * 
     * @return the username
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Get the password
     * 
     * @return The password
     */
    public String getPassword() {
        return new String(password.getPassword());
    }

}
