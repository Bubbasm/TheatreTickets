package controller.customer;

import java.awt.*;
import java.awt.event.*;

import view.*;
import view.customer.search.*;
import model.app.TheatreTickets;
import model.enums.*;
import model.users.Customer;
import model.users.notifications.*;
import model.operations.*;
import model.acts.performances.*;

import javax.swing.*;

import controller.MainController;
import view.customer.notifications.NotificationCenter;
import view.customer.notifications.NotificationEntry;
import view.customer.purchase.AreaSelection;
import view.customer.reservations.ReservationCenter;
import view.customer.reservations.ReservationEntry;

import java.time.LocalDateTime;
import java.time.format.*;

/**
 * Specific search controller for registered, creating specific performance
 * entries, reservation button handler and notification center handler.
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class SearchRegisteredController extends SearchController {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     * @param mc   Main controller, with instances of other controllers
     */
    public SearchRegisteredController(TheatreTickets tt, MainWindow view, MainController mc) {
        super(tt, view, mc);
        panel = view.getSearchRegisteredView();
    }

    /**
     * Getter for the performance entry to be generated.
     *
     * @param p    Performance to add
     * @param bool If event details must be shown
     * @param uid  Unique id for entry, to keep track of button pressed
     * @return The performance entry
     */
    @Override
    protected PerformanceEntry getPerformanceEntry(Performance p, boolean bool, Integer uid) {
        PerformanceStatus ps = p.getStatus();
        PerformanceEntry pe = new PerformanceEntry(p.getEvent().getTitle(),
                p.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), p.isSoldOut(),
                ps.equals(PerformanceStatus.CANCELLED), bool);

        if (ps.equals(PerformanceStatus.EXPIRED) || ps.equals(PerformanceStatus.CANCELLED)) {
            pe.greyOutButton();
            return pe;
        }

        if (p.isSoldOut()) {
            pe.setButtonHandler(getEnterWaitingListHandler());
        } else {
            pe.setButtonHandler(getSeeAvailabilityHandler());
        }

        pe.setButtonActionCommand(uid.toString());
        strPerformance.put(uid.toString(), p);

        Customer customer = (Customer) TheatreTickets.getCurrentCustomer();
        if (customer.getWaitingPerformances().contains(p)) {
            pe.greyOutButton();
        }

        return pe;
    }

    /**
     * Getter for waiting list button action
     *
     * @return The action listener
     */
    private ActionListener getEnterWaitingListHandler() {
        return a -> {
            Performance p = strPerformance.get(a.getActionCommand());
            Customer customer = (Customer) TheatreTickets.getCurrentCustomer();
            if (p.enterWaitingList(customer)){
                ((JButton) a.getSource()).setEnabled(false);
                JOptionPane.showMessageDialog(panel, "You will be notified when there are more tickets left", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    /**
     * Getter for see availability button action
     *
     * @return The action listener
     */
    private ActionListener getSeeAvailabilityHandler() {
        return a -> {
            Performance p = strPerformance.get(a.getActionCommand());

            AreaSelection as = new AreaSelection(mc.getTreePanelController().createTree(p));
            if (LocalDateTime.now().isAfter(p.getDate().minusHours(instance.getHoursForReservation())))
                as.disableReservation();
            view.updateAreaSelectionView(as);
            mc.setPerformance(p);

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.AreaSelectionName);
        };
    }

    /**
     * Getter for notification center button action
     *
     * @return The action listener
     */
    public ActionListener getNotificationHandler() {
        return (e -> {
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            NotificationCenter notificationCenter = view.getNotificationCenterView();
            notificationCenter.emptyEntries();
            for (Notification noti : c.getNotifications()) {
                notificationCenter.addEntry(new NotificationEntry(noti.toString()));
            }
            c.deleteNotification();
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.NotificationCenterName);
        });
    }

    /**
     * Getter for reservation center button action
     *
     * @return The action listener
     */
    public ActionListener getReservationHandler() {
        return (e -> {
            Customer c = (Customer) TheatreTickets.getCurrentCustomer();
            ReservationCenter reservationCenter = view.getReservationCenterView();
            reservationCenter.emptyEntries();
            for (Reservation res : c.getActiveReservations()) {
                ReservationEntry re = new ReservationEntry(res.toString());
                ReservationCenterController rcc = mc.getReservationCenterController();
                re.setConfirmHandler(rcc.getConfirmReservation(res));
                re.setCancelHandler(rcc.getCancelReservation(res, re));
                reservationCenter.addEntry(re);
            }

            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.ReservationCenterName);
        });
    }

    /**
     * Getter for log out button action
     *
     * @return The action listener
     */
    public ActionListener getLogOutHandler() {
        return e -> {
            instance.logout();
            Search search = view.getSearchRegisteredView();
            search.clearSearch();
            search.emptySearch();
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), MainWindow.LogInSignUpName);
        };
    }

}
