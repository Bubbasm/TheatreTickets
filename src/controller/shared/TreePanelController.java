package controller.shared;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.*;
import javax.swing.tree.*;

import model.areas.*;
import model.operations.payments.CyclePass;
import view.MainWindow;
import model.acts.cycles.Cycle;
import model.acts.performances.Performance;
import model.app.TheatreTickets;

/**
 * Controller for tree panel
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TreePanelController extends Controller {

    /**
     * constructor
     * 
     * @param tt   the main app
     * @param view main window
     */
    public TreePanelController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Creates a JTree without showing any pass prices
     * @return the tree
     */
    public JTree createTree() {
        return createTree(false);
    }

    /**
     * Creates a JTree
     * 
     * @param withPassPrices true for showing pass prices
     * @return the tree
     */
    public JTree createTree(boolean withPassPrices) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Available Areas");
        createNodesRecursive(top, instance.getMainArea(), withPassPrices);
        return new JTree(top);
    }

    /**
     * Creates a JTree with the specified root name
     * 
     * @param rootName name for the root
     * @return the tree
     */
    public JTree createTree(String rootName) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(rootName);
        createNodesRecursive(top, instance.getMainArea(), false);
        return new JTree(top);
    }

    /**
     * Creates a JTree for performances views
     * 
     * @param perf the performance
     * @return the tree
     */
    public JTree createTree(Performance perf) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Available Areas");
        createNodesRecursive(top, instance.getMainArea(), perf);
        return new JTree(top);
    }

    /**
     * Creates a JTree for cycle views
     * 
     * @param c the cycle
     * @return the tree
     */
    public JTree createTree(Cycle c) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Available Areas");
        createNodesRecursive(top, instance.getMainArea(), c);
        return new JTree(top);
    }

    /**
     * Creates the nodes of the JTree recursively
     * 
     * @param top            root node
     * @param area           current area
     * @param withPassPrices true for showing the pass prices
     */
    private void createNodesRecursive(DefaultMutableTreeNode top, Composite area, boolean withPassPrices) {
        for (Area a : area.getAreas()) {
            if (a instanceof NonComposite) {
                top.add(new DefaultMutableTreeNode(
                        withPassPrices ? createString((NonComposite) a) : createBasicString((NonComposite) a), false));
            } else if (a instanceof Composite) {
                DefaultMutableTreeNode category = new DefaultMutableTreeNode(a.getName(), true);
                top.add(category);
                createNodesRecursive(category, (Composite) a, withPassPrices);
            }
        }
    }

    /**
     * Creates the nodes of the JTree recursively
     * 
     * @param top  root node
     * @param area current area
     * @param perf performance
     */
    private void createNodesRecursive(DefaultMutableTreeNode top, Composite area, Performance perf) {
        for (Area a : area.getAreas()) {
            if (a instanceof NonComposite) {
                top.add(new DefaultMutableTreeNode(createString((NonComposite) a, perf), false));
            } else if (a instanceof Composite) {
                DefaultMutableTreeNode category = new DefaultMutableTreeNode(a.getName(), true);
                top.add(category);
                createNodesRecursive(category, (Composite) a, perf);
            }
        }
    }

    /**
     * Creates the nodes of the JTree recursively
     * 
     * @param top  root node
     * @param area current area
     * @param c    the cycle
     */
    private void createNodesRecursive(DefaultMutableTreeNode top, Composite area, Cycle c) {
        for (Area a : area.getAreas()) {
            if (a instanceof NonComposite) {
                top.add(new DefaultMutableTreeNode(createString((NonComposite) a, c), false));
            } else if (a instanceof Composite) {
                DefaultMutableTreeNode category = new DefaultMutableTreeNode(a.getName(), true);
                top.add(category);
                createNodesRecursive(category, (Composite) a, c);
            }
        }
    }

    /**
     * Create a basic string with the area name
     * 
     * @param area area to represent
     * @return the string
     */
    private String createBasicString(NonComposite area) {
        return area.getName() + " (" + (area instanceof Sitting ? "Sitting" : "Standing") + ")";
    }

    /**
     * Creates a string for a performance representation
     * 
     * @param area Area to consider
     * @param perf Performance to consider
     * @return The constructed string
     */
    private String createString(NonComposite area, Performance perf) {
        return createBasicString(area) + "\n     \nprice: " + perf.getEvent().getPrice((NonComposite) area)
                + " €/ticket     "
                + (perf.isSoldOut(area) ? "SOLD OUT" : "available: " + perf.remainingPositions(area));
    }

    /**
     * Creates a string for representation
     * 
     * @param area Area to consider
     * @return The constructed string
     */
    private String createString(NonComposite area) {
        return createBasicString(area) + "\n     \nprice: " + TheatreTickets.getInstance().getPassPrice(area) + " €";
    }

    /**
     * Creates a string for a cycle representation
     * 
     * @param area Area to consider
     * @param c    Cycle to consider
     * @return The constructed string
     */
    private String createString(NonComposite area, Cycle c) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
        df.setMinimumFractionDigits(2);
        return createBasicString(area) + "\n     \nprice: " + df.format(CyclePass.getPrice(c, area)) + " €"
                + "      discount: " + df.format(c.getDiscount(area) * 100) + "%";
    }
}
