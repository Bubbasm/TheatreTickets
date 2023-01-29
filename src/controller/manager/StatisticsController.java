package controller.manager;

import java.awt.event.*;
import java.util.*;

import javax.swing.JOptionPane;

import controller.shared.Controller;
import model.acts.events.Event;
import model.acts.performances.Performance;
import model.app.TheatreTickets;
import model.areas.*;
import view.MainWindow;
import view.manager.enums.*;
import view.manager.stats.Statistics;

/**
 * Controller for statistics
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class StatisticsController extends Controller implements ActionListener {
    /**
     * Constructor
     * 
     * @param tt   The main app
     * @param view Main window
     */
    public StatisticsController(TheatreTickets tt, MainWindow view) {
        super(tt, view);
    }

    /**
     * Implements actions performed when statistics about performance, event with
     * specific filter
     *
     * @param ae ActionEvent, ignored
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        Statistics s = view.getStatisticsView();
        Event e = instance.searchEventByName(s.getEventName());
        if (e == null) {
            JOptionPane.showMessageDialog(s, "An error has ocurred", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (e.getPerformances().isEmpty()) {
            JOptionPane.showMessageDialog(s, "This event has no performances, please select another one", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Performance p = e.getPerformanceByDate(s.getPerformanceDate());

        Map<NonComposite, Double> stats = new HashMap<>();
        if (s.getType().equals(StatisticsType.ATTENDANCE)) {
            for (NonComposite nc : instance.getMainArea().getNonCompositeWithin()) {
                stats.put(nc, p.getAttendanceNum(nc));
            }
        } else {
            for (NonComposite nc : instance.getMainArea().getNonCompositeWithin()) {
                stats.put(nc, p.getRevenue(nc));
            }
        }

        List<NonComposite> sorted = new LinkedList<>();
        sorted.addAll(instance.getMainArea().getNonCompositeWithin());
        sorted.sort(new Comparator<NonComposite>() {
            public int compare(NonComposite k1, NonComposite k2) {
                int comp = stats.get(k1).compareTo(stats.get(k2));
                if (comp == 0)
                    return 1;
                else
                    return (s.getFilter().equals(StatisticsFilter.HIGHER_FIRST) ? -1 : 1) * comp;
            }
        });

        List<String> list = new LinkedList<>();
        for (NonComposite nc : sorted) {
            String str = nc.getName() + " " + (nc instanceof Sitting ? "(Sitting)" : "(Standing)") + ": ";
            if (s.getType().equals(StatisticsType.ATTENDANCE)) {
                list.add(str + "Occupied " + Math.round(stats.get(nc)) + " positions out of "
                        + e.getRestrictedCapacity(nc));
            } else {
                list.add(str + "Gained " + stats.get(nc) + " € in this area");
            }
        }

        s.updateStats(list);
    }

}
