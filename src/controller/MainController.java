package controller;

import java.awt.event.*;

import view.*;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import model.areas.NonComposite;

import java.util.*;

import controller.customer.*;
import controller.manager.*;
import controller.shared.*;

/**
 * Main controller class, in charge of synchronizing all controllers
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class MainController {
    /** Name for the login controller */
    public static final String LogInSignUpController = "LogInSignUpController";
    /** Name for the unregistered search controller */
    public static final String UnregisteredSearchController = "SearchUnregisteredController";
    /** Name for the registered search controller */
    public static final String RegisteredSearchController = "SearchRegisteredController";
    /** Name for the area selection controller */
    public static final String AreaSelectionController = "AreaSelectionController";
    /** Name for the manual seat selection controller */
    public static final String ManualSelectionController = "ManualSeatSelectionController";
    /** Name for the automatic seat selection controller */
    public static final String AutomaticSelectionController = "AutomaticSeatSelectionController";
    /** Name for the standing selection controller */
    public static final String StandingPosSelectionController = "StandingPositionsSelectionController";
    /** Name for the payment selection controller */
    public static final String PaymentSelectionController = "PaymentSelectionController";
    /** Name for the annual pass purchase controller */
    public static final String AnnualPassPurchaseController = "AnnualPassPurchaseController";
    /** Name for the cycle pass purchase controller */
    public static final String CyclePassPurchaseController = "CyclePassPurchaseController";
    /** Name for the back button controller */
    public static final String BackButtonController = "BackButtonController";
    /** Name for the menu controller */
    public static final String MenuController = "MenuController";
    /** Name for the add event controller */
    public static final String AddEventController = "AddEventController";
    /** Name for the add event controller */
    public static final String AddCycleController = "AddCycleController";
    /** Name for the add performance controller */
    public static final String AddPerformanceController = "AddPerformanceController";
    /** Name for the reservation center */
    public static final String ReservationCenterController = "ReservationCenterController";
    /** Name for the additional settings */
    public static final String AdditionalSettingsController = "AdditionalSettingsController";
    /** Name for the edit perfs settings */
    public static final String EditPerformancesController = "EditPerformancesController";
    /** Name for the disable seats controller */
    public static final String DisableSeatsController = "DisableSeatsController";
    /** Name for the stats controller */
    public static final String StatisticsController = "StatisticsController";
    /** Name for the modify theatre controller */
    public static final String ModifyTheatreController = "ModifyTheatreController";
    /** Name for the tree panel controller */
    public static final String TreePanelController = "TreePanelController";

    /** Current performance selected */
    private Performance currentPerformance;
    /** Current area selected */
    private NonComposite currentArea;
    /** Mapping of strings and the controllers */
    private Map<String, Controller> controllers = new HashMap<>();

    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view The main window
     */
    public MainController(TheatreTickets tt, MainWindow view) {

        /* Customer */
        controllers.put(LogInSignUpController, new LogInSignUpController(tt, view));
        controllers.put(UnregisteredSearchController, new SearchUnregisteredController(tt, view, this));
        controllers.put(RegisteredSearchController, new SearchRegisteredController(tt, view, this));
        controllers.put(AreaSelectionController, new AreaSelectionController(tt, view, this));
        controllers.put(ManualSelectionController, new ManualSeatSelectionController(tt, view, this));
        controllers.put(AutomaticSelectionController, new AutomaticSeatSelectionController(tt, view, this));
        controllers.put(StandingPosSelectionController, new StandingPositionsSelectionController(tt, view, this));
        controllers.put(ReservationCenterController, new ReservationCenterController(tt, view, this));
        controllers.put(PaymentSelectionController, new PaymentSelectionController(tt, view, this));
        controllers.put(AnnualPassPurchaseController, new AnnualPassPurchaseController(tt, view, this));
        controllers.put(CyclePassPurchaseController, new CyclePassPurchaseController(tt, view, this));
        controllers.put(BackButtonController, new BackButtonHandler(tt, view));
        controllers.put(DisableSeatsController, new DisableSeatsController(tt, view));
        controllers.put(StatisticsController, new StatisticsController(tt, view));
        controllers.put(ModifyTheatreController, new ModifyTheatreController(tt, view, this));
        controllers.put(TreePanelController, new TreePanelController(tt, view));

        /* Manager */
        controllers.put(MenuController, new MenuController(tt, view, this));
        controllers.put(AddEventController, new AddEventController(tt, view));
        controllers.put(AddCycleController, new AddCycleController(tt, view));
        controllers.put(AddPerformanceController, new AddPerformanceController(tt, view));
        controllers.put(AdditionalSettingsController, new AdditionalSettingsController(tt, view));
        controllers.put(EditPerformancesController, new EditPerformancesController(tt, view));
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public LogInSignUpController getLogInSignUpHandler() {
        return (LogInSignUpController) controllers.get(LogInSignUpController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public SearchUnregisteredController getSearchUnregisteredHandler() {
        return (SearchUnregisteredController) controllers.get(UnregisteredSearchController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public SearchRegisteredController getSearchRegisteredHandler() {
        return (SearchRegisteredController) controllers.get(RegisteredSearchController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public ReservationCenterController getReservationCenterController() {
        return (ReservationCenterController) controllers.get(ReservationCenterController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AreaSelectionController getAreaSelectionController() {
        return (AreaSelectionController) controllers.get(AreaSelectionController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public ManualSeatSelectionController getManualSeatSelectionController() {
        return (ManualSeatSelectionController) controllers.get(ManualSelectionController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AutomaticSeatSelectionController getAutomaticSeatSelectionController() {
        return (AutomaticSeatSelectionController) controllers.get(AutomaticSelectionController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public StandingPositionsSelectionController getStandingPositionsSelectionController() {
        return (StandingPositionsSelectionController) controllers.get(StandingPosSelectionController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public PaymentSelectionController getPaymentSelectionController() {
        return (PaymentSelectionController) controllers.get(PaymentSelectionController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AnnualPassPurchaseController getAnnualPassPurchaseController() {
        return (AnnualPassPurchaseController) controllers.get(AnnualPassPurchaseController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public CyclePassPurchaseController getCyclePassPurchaseController() {
        return (CyclePassPurchaseController) controllers.get(CyclePassPurchaseController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AddEventController getAddEventController() {
        return (AddEventController) controllers.get(AddEventController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AddCycleController getAddCycleController() {
        return (AddCycleController) controllers.get(AddCycleController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public ModifyTheatreController getModifyTheatreController() {
        return (ModifyTheatreController) controllers.get(ModifyTheatreController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public ActionListener getBackButtonHandler() {
        return (BackButtonHandler) controllers.get(BackButtonController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public MenuController getMenuHandler() {
        return (MenuController) controllers.get(MenuController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AddPerformanceController getAddPerformanceController() {
        return (AddPerformanceController) controllers.get(AddPerformanceController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public AdditionalSettingsController getAdditionalSettingsController() {
        return (AdditionalSettingsController) controllers.get(AdditionalSettingsController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public EditPerformancesController getEditPerformancesController() {
        return (EditPerformancesController) controllers.get(EditPerformancesController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public DisableSeatsController getDisableSeatsController() {
        return (DisableSeatsController) controllers.get(DisableSeatsController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public StatisticsController getStatisticsController() {
        return (StatisticsController) controllers.get(StatisticsController);
    }

    /**
     * Getter for a controller
     * 
     * @return The controller
     */
    public TreePanelController getTreePanelController() {
        return (TreePanelController) controllers.get(TreePanelController);
    }

    /**
     * Sets current performance
     * 
     * @param p Performance to set
     */
    public void setPerformance(Performance p) {
        currentPerformance = p;
    }

    /**
     * Gets current performance
     * 
     * @return the performance
     */
    public Performance getPerformance() {
        return currentPerformance;
    }

    /**
     * Sets current area selected
     * 
     * @param nc Area to set
     */
    public void setNonComposite(NonComposite nc) {
        currentArea = nc;
    }

    /**
     * Gets current area
     * 
     * @return The area
     */
    public NonComposite getNonComposite() {
        return currentArea;
    }
}
