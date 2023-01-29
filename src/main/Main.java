package main;

import controller.MainController;
import model.app.TheatreTickets;
import view.MainWindow;
import java.awt.event.*;

/**
 * Main class to execute app
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Main {
    /** Path for the serialized application */
    private static final String pathToSave = "./data/TheatreTickets.save";

    /**
     * Application entry point
     * 
     * @param args arguments to be passed
     */
    public static void main(String[] args) {
        try {
            TheatreTickets.loadFrom(pathToSave);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("NOTE: This error is expected when executing the app for the first time");
        }
        TheatreTickets mainApp = TheatreTickets.getInstance();
        MainWindow mainWindow = new MainWindow();
        MainController mainController = new MainController(mainApp, mainWindow);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                TheatreTickets.saveTo(pathToSave);
            }
        });
        mainWindow.setMainHandler(mainController);
    }
}
