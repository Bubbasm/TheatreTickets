package controller.manager;

import java.awt.event.*;

import javax.swing.JOptionPane;

import controller.shared.Controller;
import model.app.TheatreTickets;
import view.MainWindow;
import view.manager.settings.AdditionalSettings;

/**
 * Class handling additional settings controller. As only one action event is
 * considered, it is decided to implement ActionListener
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AdditionalSettingsController extends Controller implements ActionListener {

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public AdditionalSettingsController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Implementation of save changes button in additional settings
     * 
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        AdditionalSettings as = view.getAdditionalSettingsView();

        instance.setMaxTicketsPerPurchase(as.getMaxTicketsPerPurchase());
        instance.setMaxTicketsPerReservation(as.getMaxTicketsPerReservation());
        instance.setHoursForReservation(as.getHoursForReservation());

        JOptionPane.showMessageDialog(as, "Settings saved succesfully", "Settings saved",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
