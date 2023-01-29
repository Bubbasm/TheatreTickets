package controller.shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import view.*;
import view.shared.*;
import model.app.TheatreTickets;
import model.users.*;
import model.exceptions.*;

/**
 * Controller in charge of login and register screen
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class LogInSignUpController extends Controller {
    /** Login panel */
    private LogInSignUp panel;
    /** Registered user */
    private User reg;

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public LogInSignUpController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
        panel = view.getLogInSignUpView();
    }

    /**
     * Getter got skip log in button
     * 
     * @return The action listener
     */
    public ActionListener getSkipHandler() {
        return (a -> {
            panel.clearFields();
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.SearchUnregisteredName);
        });
    }

    /**
     * Getter for login button
     * 
     * @return The action listener
     */
    public ActionListener getLoginHandler() {
        return a -> {
            try {
                reg = instance.login(panel.getUsername(), panel.getPassword());
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                if (reg instanceof Customer) {
                    cl.show(view.getContentPane(), MainWindow.SearchRegisteredName);
                } else if (reg instanceof Manager) {
                    cl.show(view.getContentPane(), MainWindow.MenuName);
                } else {
                    JOptionPane.showMessageDialog(panel, "Unhandled kind of user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (InvalidLoginException e) {
                JOptionPane.showMessageDialog(panel, "Username or password not valid.", "Error loging in",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                panel.clearFields();
            }
        };
    }

    /**
     * Getter for sign up button
     * 
     * @return The action listener
     */
    public ActionListener getSignUpHandler() {
        return e -> {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.RegisterName);
        };
    }

    /**
     * Getter for register button
     * 
     * @return the action listener
     */
    public ActionListener getRegisterHandler() {
        return (e -> {
            Register register = view.getRegisterView();
            int minLen = 4;
            if (register.getUsername().length() < minLen || register.getPassword().length() < minLen) {
                JOptionPane.showMessageDialog(panel,
                        "Username and password length must be at least " + minLen + " characters", "Error registering",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Customer c = instance.register(register.getUsername(), register.getPassword());
                JOptionPane.showMessageDialog(register,
                        c.getUsername() + ", welcome to our application TheatreTickets!", "Welcome to TheatreTickets",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (InvalidUsernameException iue) {
                JOptionPane.showMessageDialog(register, iue.getMessage(), "Error registering",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println(iue.getMessage());
            } finally {
                register.clearFields();
                view.getLogInSignUpView().clearFields();
                CardLayout cl = (CardLayout) view.getContentPane().getLayout();
                cl.show(view.getContentPane(), MainWindow.LogInSignUpName);
            }

        });
    }
}
