package view;

import view.customer.purchase.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import controller.*;
import controller.customer.AnnualPassPurchaseController;
import controller.customer.AutomaticSeatSelectionController;
import controller.customer.CyclePassPurchaseController;
import controller.customer.SearchRegisteredController;
import controller.customer.SearchUnregisteredController;
import controller.customer.StandingPositionsSelectionController;
import controller.manager.AddCycleController;
import controller.manager.AddEventController;
import controller.manager.AddPerformanceController;
import controller.manager.AdditionalSettingsController;
import controller.manager.DisableSeatsController;
import controller.manager.EditPerformancesController;
import controller.manager.MenuController;
import controller.manager.ModifyTheatreController;
import controller.shared.LogInSignUpController;
import view.shared.*;
import view.customer.search.*;
import view.manager.addCycle.AddCycleFirst;
import view.manager.addCycle.AddCycleSecond;
import view.manager.addEvent.AddEventFirst;
import view.manager.addEvent.AddEventSecond;
import view.manager.addPerformance.AddPerformance;
import view.manager.disableSeats.DisableSeatsFirst;
import view.manager.disableSeats.DisableSeatsSecond;
import view.manager.editPerformance.EditPerformance;
import view.manager.menu.Menu;
import view.manager.modifyTheatre.ModifyTheatreFirst;
import view.manager.modifyTheatre.ModifyTheatreSecond;
import view.manager.settings.AdditionalSettings;
import view.manager.stats.Statistics;
import view.customer.reservations.*;
import view.customer.notifications.*;

import java.util.*;

/**
 * Main window, keeping account of all views
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class MainWindow extends JFrame {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Content pane */
    private Container container;
    /** Main controller */
    private MainController mc;
    /** Mapping of names and the views */
    private Map<String, JPanel> views = new HashMap<>();

    /** Name for the login view */
    public static final String LogInSignUpName = "LogInSignUpName";
    /** Name for the register view */
    public static final String RegisterName = "RegisterName";
    /** Name for the unregistered search view */
    public static final String SearchUnregisteredName = "SearchUnregisteredName";
    /** Name for the registered search view */
    public static final String SearchRegisteredName = "SearchRegisteredName";
    /** Name for the reservations view */
    public static final String ReservationCenterName = "ReservationCenterName";
    /** Name for the Event detailed view */
    public static final String EventEntryDetailedName = "EventEntryDetailedName";
    /** Name for the Cycle detailed view */
    public static final String CycleEntryDetailedName = "CycleEntryDetailedName";
    /** Name for the area selection view */
    public static final String AreaSelectionName = "AreaSelectionName";
    /** Name for the manual seat selection view */
    public static final String ManualSeatSelectionName = "ManualSeatSelectionName";
    /** Name for the automatic seat selection view */
    public static final String AutomaticSeatSelectionName = "AutomaticSeatSelectionName";
    /** Name for the annual pass purchase view */
    public static final String AnnualPassPurchaseName = "AnnualPassPurchaseName";
    /** Name for the cycle pass purchase view */
    public static final String CyclePassPurchaseName = "CyclePassPurchaseName";
    /** Name for the payment selection view */
    public static final String PaymentSelectionName = "PaymentSelectionName";
    /** Name for the notifications view */
    public static final String NotificationCenterName = "NotificationCenterName";
    /** Name for the standing positions view */
    public static final String StandingPositionsSelectionName = "StandingPositionsSelectionName";
    /** Name for the Menu view */
    public static final String MenuName = "MenuName";
    /** Name for the add event view */
    public static final String AddEventFirstName = "AddEventFirstName";
    /** Name for the add event view */
    public static final String AddEventSecondName = "AddEventSecondName";
    /** Name for the add performance view */
    public static final String AddPerformanceName = "AddPerformanceName";
    /** Name for the edit performance view */
    public static final String EditPerformanceName = "EditPerformanceName";
    /** Name for the add performance view */
    public static final String AddCycleFirstName = "AddCycleFirstName";
    /** Name for the add performance view */
    public static final String AddCycleSecondName = "AddCycleSecondName";
    /** Name for the addit settings view */
    public static final String AdditionalSettingsName = "AdditionalSettingsName";
    /** Name for the settings view */
    public static final String SettingsName = "SettingsName";
    /** Name for the stats view */
    public static final String StatisticsName = "StatisticsName";
    /** Name for the modify theatre view part 1 */
    public static final String ModifyTheatreFirstName = "ModifyTheatreFirstName";
    /** Name for the modify theatre view part 2 */
    public static final String ModifyTheatreSecondName = "ModifyTheatreSecondName";
    /** Name for the disable seats view */
    public static final String DisableSeatsFirstName = "DisableSeatsFirstName";
    /** Name for the disable seats second view */
    public static final String DisableSeatsSecondName = "DisableSeatsSecondName";

    /**
     * Constructor
     */
    public MainWindow() {
        super("TheatreTickets");
        container = this.getContentPane();
        container.setLayout(new CardLayout());

        LogInSignUp logIn = new LogInSignUp();
        Register register = new Register();
        SearchUnregistered searchUnregistered = new SearchUnregistered();
        SearchRegistered searchRegistered = new SearchRegistered();
        ReservationCenter reservationCenter = new ReservationCenter();
        NotificationCenter notificationCenter = new NotificationCenter();
        Menu menu = new Menu();
        AddEventFirst addEventFirst = new AddEventFirst();
        AddEventSecond addEventSecond = new AddEventSecond();
        AddPerformance addPerformance = new AddPerformance();
        AdditionalSettings additionalSettings = new AdditionalSettings();
        EditPerformance editPerfs = new EditPerformance();
        ModifyTheatreSecond modifyTheatreSecond = new ModifyTheatreSecond();

        views.put(LogInSignUpName, logIn);
        views.put(RegisterName, register);
        views.put(SearchUnregisteredName, searchUnregistered);
        views.put(SearchRegisteredName, searchRegistered);
        views.put(ReservationCenterName, reservationCenter);
        views.put(NotificationCenterName, notificationCenter);
        views.put(MenuName, menu);
        views.put(AddEventFirstName, addEventFirst);
        views.put(AddEventSecondName, addEventSecond);
        views.put(AddPerformanceName, addPerformance);
        views.put(AdditionalSettingsName, additionalSettings);
        views.put(EditPerformanceName, editPerfs);
        views.put(ModifyTheatreSecondName, modifyTheatreSecond);

        for (Map.Entry<String, JPanel> entry : views.entrySet()) {
            container.add(entry.getValue(), entry.getKey());
        }

        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, MainWindow.LogInSignUpName);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setVisible(true);

    }

    /**
     * Setter for the main handler
     * 
     * @param mc main controller
     */
    public void setMainHandler(MainController mc) {
        this.mc = mc;
        setLogInSignUpController(mc.getLogInSignUpHandler());
        setSearchRegisteredController(mc.getSearchRegisteredHandler());
        setSearchUnregisteredController(mc.getSearchUnregisteredHandler());
        setMenuController(mc.getMenuHandler());
        setAddEventController(mc.getAddEventController());
        setAddPerformanceController(mc.getAddPerformanceController());
        setAdditionalSettingsController(mc.getAdditionalSettingsController());
        setEditPerformancesController(mc.getEditPerformancesController());
        setModifyTheatreSecondController(mc.getModifyTheatreController());

        setBackButtonHandler(getReservationCenterView());
        setBackButtonHandler(getNotificationCenterView());
        setBackButtonHandler(getAddEventFirstView());
        setBackButtonHandler(getAddEventSecondView());
        setBackButtonHandler(getAddPerformanceView());
        setBackButtonHandler(getAdditionalSettingsView());
        setBackButtonHandler(getEditPerformancesView());
    }

    /**
     * Private setter for the controllers
     * 
     * @param lisuc controller to be set
     */
    private void setLogInSignUpController(LogInSignUpController lisuc) {
        LogInSignUp aux = getLogInSignUpView();
        aux.setSkipHandler(lisuc.getSkipHandler());
        aux.setLoginHandler(lisuc.getLoginHandler());
        aux.setSignUpHandler(lisuc.getSignUpHandler());
        Register reg = getRegisterView();
        reg.setRegisterHandler(lisuc.getRegisterHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param suc controller to be set
     */
    private void setSearchUnregisteredController(SearchUnregisteredController suc) {
        SearchUnregistered aux = getSearchUnregisteredView();
        aux.setSearchHandler(suc.getSearchHandler());
        aux.setLogInNowHandler(suc.getLogInNowHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param src controller to be set
     */
    private void setSearchRegisteredController(SearchRegisteredController src) {
        SearchRegistered aux = getSearchRegisteredView();
        aux.setSearchHandler(src.getSearchHandler());
        aux.setLogOutHandler(src.getLogOutHandler());
        aux.setNotificationHandler(src.getNotificationHandler());
        aux.setReservationsHandler(src.getReservationHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param mec controller to be set
     */
    private void setMenuController(MenuController mec) {
        Menu aux = getMenuView();
        aux.setAddEventHandler(mec.getAddEventHandler());
        aux.setAddCycleHandler(mec.getAddCycleHandler());
        aux.setAddPerformanceHandler(mec.getAddPerformanceHandler());
        aux.setEditPerformancesHandler(mec.getEditPerformanceHandler());
        aux.setDisableSeatsHandler(mec.getDisableSeatsHandler());
        aux.setStatisticsHandler(mec.getStatisticsHandler());
        aux.setModifyTheatreHandler(mec.getModifyTheatreHandler());
        aux.setAdditionalSettingsHandler(mec.getAdditionalSettingsHandler());
        aux.setLogoutHandler(mec.getLogOutHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param aec controller to be set
     */
    private void setAddEventController(AddEventController aec) {
        AddEventFirst aef = getAddEventFirstView();
        AddEventSecond aes = getAddEventSecondView();
        aef.setContinueButtonHandler(aec.getAddEventFirstHandler());
        aes.setContinueButtonHandler(aec.getAddEventSecondHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param aec controller to be set
     */
    private void setAddPerformanceController(AddPerformanceController apc) {
        AddPerformance ap = getAddPerformanceView();
        ap.setContinueButtonHandler(apc);
    }

    /**
     * Private setter for the controllers
     * 
     * @param aec controller to be set
     */
    private void setAdditionalSettingsController(AdditionalSettingsController asc) {
        AdditionalSettings as = getAdditionalSettingsView();
        as.setContinueButtonHandler(asc);
    }

    /**
     * Private setter for the controllers
     * 
     * @param aec controller to be set
     */
    private void setEditPerformancesController(EditPerformancesController epc) {
        EditPerformance ep = getEditPerformancesView();
        ep.setPostponePerformanceHandler(epc.getPostponeHandler());
        ep.setCancelPerformanceHandler(epc.getCancelHandler());
    }

    /**
     * Private setter for the controllers
     * 
     * @param aec controller to be set
     */
    private void setModifyTheatreSecondController(ModifyTheatreController mtc) {
        ModifyTheatreSecond mts = getModifyTheatreSecondView();
        mts.setContinueButtonHandler(mtc.getAddAreaSecondHandler());
        mts.setBackButtonHandler(mtc.getBackButtonHandler());
    }

    /**
     * Setter for the back button handler
     * 
     * @param tp panel
     */
    public void setBackButtonHandler(TemplatePanel tp) {
        tp.setBackButtonHandler(mc.getBackButtonHandler());
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public LogInSignUp getLogInSignUpView() {
        return (LogInSignUp) views.get(LogInSignUpName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public Register getRegisterView() {
        return (Register) views.get(RegisterName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public SearchUnregistered getSearchUnregisteredView() {
        return (SearchUnregistered) views.get(SearchUnregisteredName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public SearchRegistered getSearchRegisteredView() {
        return (SearchRegistered) views.get(SearchRegisteredName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public ReservationCenter getReservationCenterView() {
        return (ReservationCenter) views.get(ReservationCenterName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public NotificationCenter getNotificationCenterView() {
        return (NotificationCenter) views.get(NotificationCenterName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public EventEntryDetailed getEventEntryDetailedView() {
        return (EventEntryDetailed) views.get(EventEntryDetailedName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public CycleEntryDetailed getCycleEntryDetailedView() {
        return (CycleEntryDetailed) views.get(CycleEntryDetailedName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AreaSelection getAreaSelectionView() {
        return (AreaSelection) views.get(AreaSelectionName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AnnualPassPurchase getAnnualPassPurchaseView() {
        return (AnnualPassPurchase) views.get(AnnualPassPurchaseName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public CyclePassPurchase getCyclePassPurchaseView() {
        return (CyclePassPurchase) views.get(CyclePassPurchaseName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public PaymentSelection getPaymentSelectionView() {
        return (PaymentSelection) views.get(PaymentSelectionName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public ManualSeatSelection getManualSeatSelectionView() {
        return (ManualSeatSelection) views.get(ManualSeatSelectionName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AutomaticSeatSelection getAutomaticSeatSelectionView() {
        return (AutomaticSeatSelection) views.get(AutomaticSeatSelectionName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public StandingPositionsSelection getStandingPositionsSelectionView() {
        return (StandingPositionsSelection) views.get(StandingPositionsSelectionName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public Menu getMenuView() {
        return (Menu) views.get(MenuName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AddEventFirst getAddEventFirstView() {
        return (AddEventFirst) views.get(AddEventFirstName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AddEventSecond getAddEventSecondView() {
        return (AddEventSecond) views.get(AddEventSecondName);
    }
 /**
     * Getter for the view
     * 
     * @return the view
     */
    public AddCycleFirst getAddCycleFirstView() {
        return (AddCycleFirst) views.get(AddCycleFirstName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AddCycleSecond getAddCycleSecondView() {
        return (AddCycleSecond) views.get(AddCycleSecondName);
    }
    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AddPerformance getAddPerformanceView() {
        return (AddPerformance) views.get(AddPerformanceName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public AdditionalSettings getAdditionalSettingsView() {
        return (AdditionalSettings) views.get(AdditionalSettingsName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public EditPerformance getEditPerformancesView() {
        return (EditPerformance) views.get(EditPerformanceName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public DisableSeatsFirst getDisableSeatsFirstView() {
        return (DisableSeatsFirst) views.get(DisableSeatsFirstName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public DisableSeatsSecond getDisableSeatsSecondView() {
        return (DisableSeatsSecond) views.get(DisableSeatsSecondName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public Statistics getStatisticsView() {
        return (Statistics) views.get(StatisticsName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public ModifyTheatreFirst getModifyTheatreFirstView() {
        return (ModifyTheatreFirst) views.get(ModifyTheatreFirstName);
    }

    /**
     * Getter for the view
     * 
     * @return the view
     */
    public ModifyTheatreSecond getModifyTheatreSecondView() {
        return (ModifyTheatreSecond) views.get(ModifyTheatreSecondName);
    }

    /**
     * Update the view
     * 
     * @param eventEntryDetailed the view to update
     */
    public void updateEventEntryDetailedView(EventEntryDetailed eventEntryDetailed) {
        JPanel eed = views.put(EventEntryDetailedName, eventEntryDetailed);
        if (eed != null) {
            container.remove(eed);
        }
        setBackButtonHandler(eventEntryDetailed);
        container.add(EventEntryDetailedName, eventEntryDetailed);
    }

    /**
     * Update the view
     * 
     * @param cycleEntryDetailed the view to update
     */
    public void updateCycleEntryDetailedView(CycleEntryDetailed cycleEntryDetailed) {
        JPanel ced = views.put(CycleEntryDetailedName, cycleEntryDetailed);
        if (ced != null) {
            container.remove(ced);
        }
        setBackButtonHandler(cycleEntryDetailed);
        container.add(CycleEntryDetailedName, cycleEntryDetailed);
    }

    /**
     * Update the view
     * 
     * @param areaSelection the view to update
     */
    public void updateAreaSelectionView(AreaSelection areaSelection) {
        JPanel as = views.put(AreaSelectionName, areaSelection);
        if (as != null) {
            container.remove(as);
        }
        areaSelection.setContinueButtonHandler(mc.getAreaSelectionController());
        setBackButtonHandler(areaSelection);
        container.add(AreaSelectionName, areaSelection);
    }

    /**
     * Update the view
     * 
     * @param annualPassPurchase the view to update
     */
    public void updateAnnualPassPurchaseView(AnnualPassPurchase annualPassPurchase) {
        JPanel app = views.put(AnnualPassPurchaseName, annualPassPurchase);
        if (app != null) {
            container.remove(app);
        }
        AnnualPassPurchaseController appc = mc.getAnnualPassPurchaseController();
        annualPassPurchase.setContinueButtonHandler(appc);
        annualPassPurchase.setBackButtonHandler(appc.getBackButtonHandler());
        container.add(AnnualPassPurchaseName, annualPassPurchase);
    }

    /**
     * Update the view
     * 
     * @param cyclePassPurchase the view to update
     */
    public void updateCyclePassPurchaseView(CyclePassPurchase cyclePassPurchase) {
        JPanel cpp = views.put(CyclePassPurchaseName, cyclePassPurchase);
        if (cpp != null) {
            container.remove(cpp);
        }
        CyclePassPurchaseController cppc = mc.getCyclePassPurchaseController();
        cyclePassPurchase.setContinueButtonHandler(cppc);
        cyclePassPurchase.setBackButtonHandler(cppc.getBackButtonHandler());
        container.add(CyclePassPurchaseName, cyclePassPurchase);
    }

    /**
     * Update the view
     * 
     * @param paymentSelection the view to update
     */
    public void updatePaymentSelectionView(PaymentSelection paymentSelection) {
        JPanel ps = views.put(PaymentSelectionName, paymentSelection);
        if (ps != null) {
            container.remove(ps);
        }
        ActionListener annualPass = mc.getPaymentSelectionController().getBuyAnnualPassButtonHandler();
        paymentSelection.setBuyAnnualPassButtonHandler(annualPass);
        ActionListener cyclePass = mc.getPaymentSelectionController().getBuyCyclePassButtonHandler();
        paymentSelection.setBuyCyclePassButtonHandler(cyclePass);
        setBackButtonHandler(paymentSelection);
        container.add(PaymentSelectionName, paymentSelection);
    }

    /**
     * Update the view
     * 
     * @param manualSeatSelection the view to update
     */
    public void updateManualSeatSelectionView(ManualSeatSelection manualSeatSelection) {
        JPanel mss = views.put(ManualSeatSelectionName, manualSeatSelection);
        if (mss != null) {
            container.remove(mss);
        }
        manualSeatSelection.setContinueButtonHandler(mc.getManualSeatSelectionController());
        setBackButtonHandler(manualSeatSelection);
        container.add(ManualSeatSelectionName, manualSeatSelection);
    }

    /**
     * Update the view
     * 
     * @param automaticSeatSelection the view to update
     */
    public void updateAutomaticSeatSelectionView(AutomaticSeatSelection automaticSeatSelection) {
        JPanel ass = views.put(AutomaticSeatSelectionName, automaticSeatSelection);
        if (ass != null) {
            container.remove(ass);
        }
        AutomaticSeatSelectionController assc = mc.getAutomaticSeatSelectionController();
        automaticSeatSelection.setContinueButtonHandler(assc);
        automaticSeatSelection.setPreviewButtonHandler(assc.getPreviewButtonHandler());
        setBackButtonHandler(automaticSeatSelection);
        container.add(AutomaticSeatSelectionName, automaticSeatSelection);
    }

    /**
     * Update the view
     * 
     * @param standingPositionsSelection the view to update
     */
    public void updateStandingPositionsSelectionView(StandingPositionsSelection standingPositionsSelection) {
        JPanel sps = views.put(StandingPositionsSelectionName, standingPositionsSelection);
        if (sps != null) {
            container.remove(sps);
        }
        StandingPositionsSelectionController spsc = mc.getStandingPositionsSelectionController();
        standingPositionsSelection.setContinueButtonHandler(spsc);
        setBackButtonHandler(standingPositionsSelection);
        container.add(StandingPositionsSelectionName, standingPositionsSelection);
    }

    /**
     * Update the view
     * 
     * @param addCycle the view to update
     */
    public void updateAddCycleFirstView(AddCycleFirst addCycle) {
        JPanel ac = views.put(AddCycleFirstName, addCycle);
        if (ac != null) {
            container.remove(ac);
        }
        AddCycleController acc = mc.getAddCycleController();
        addCycle.setContinueButtonHandler(acc.getAddCycleFirstHandler());
        setBackButtonHandler(addCycle);
        container.add(AddCycleFirstName, addCycle);
    }

    /**
     * Update the view
     * 
     * @param addCycle the view to update
     */
    public void updateAddCycleSecondView(AddCycleSecond addCycle) {
        JPanel ac = views.put(AddCycleSecondName, addCycle);
        if (ac != null) {
            container.remove(ac);
        }
        AddCycleController acc = mc.getAddCycleController();
        addCycle.setContinueButtonHandler(acc.getAddCycleSecondHandler());
        addCycle.setBackButtonHandler(acc.getBackButtonHandler());
        container.add(AddCycleSecondName, addCycle);
    }

    /**
     * Update the view
     * 
     * @param disableSeats the view to update
     */
    public void updateDisableSeatsFirstView(DisableSeatsFirst disableSeats) {
        JPanel ds = views.put(DisableSeatsFirstName, disableSeats);
        if (ds != null) {
            container.remove(ds);
        }
        DisableSeatsController dcs = mc.getDisableSeatsController();
        disableSeats.setContinueButtonHandler(dcs.getContinueButtonFirstHandler());
        disableSeats.setBackButtonHandler(mc.getBackButtonHandler());
        setBackButtonHandler(disableSeats);
        container.add(DisableSeatsFirstName, disableSeats);
    }

    /**
     * Update the view
     * 
     * @param disableSeats the view to update
     */
    public void updateDisableSeatsSecondView(DisableSeatsSecond disableSeats) {
        JPanel ds = views.put(DisableSeatsSecondName, disableSeats);
        if (ds != null) {
            container.remove(ds);
        }
        DisableSeatsController dcs = mc.getDisableSeatsController();
        disableSeats.setContinueButtonHandler(dcs.getContinueButtonSecondHandler());
        disableSeats.setBackButtonHandler(mc.getBackButtonHandler());
        setBackButtonHandler(disableSeats);
        container.add(DisableSeatsSecondName, disableSeats);
    }

    /**
     * Update the view
     * 
     * @param stats the view to update
     */
    public void updateStatisticsView(Statistics stats) {
        JPanel ds = views.put(StatisticsName, stats);
        if (ds != null) {
            container.remove(ds);
        }
        stats.setStatsButtonHandler(mc.getStatisticsController());
        setBackButtonHandler(stats);
        container.add(StatisticsName, stats);
    }

    /**
     * Update the view
     * 
     * @param modifTheatre the view to update
     */
    public void updateModifyTheatreFirstView(ModifyTheatreFirst modifTheatre) {
        JPanel mt = views.put(ModifyTheatreFirstName, modifTheatre);
        if (mt != null) {
            container.remove(mt);
        }
        modifTheatre.setAddAreaHandler(mc.getModifyTheatreController().getAddAreaFirstHandler());
        setBackButtonHandler(modifTheatre);
        container.add(ModifyTheatreFirstName, modifTheatre);
    }
}
