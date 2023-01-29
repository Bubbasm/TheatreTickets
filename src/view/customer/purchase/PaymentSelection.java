package view.customer.purchase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;

import view.shared.TemplatePanel;
import view.shared.TextField;
/**
 * View for the payment selection
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class PaymentSelection extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Button to buy an annual pass */
    private JButton buyAnnualPass;
    /** Button to buy an cycle pass */
    private JButton buyCyclePass;
    /** Integer containing annual passes used to pay */
    private MyInteger numAnnual;
    /** Integer containing cycle passes used to pay */
    private MyInteger numCycle;
    /** Integer containing tickets to be paid with creditcard */
    private MyInteger numCreditCard;
    /** Credit card number */
    private TextField ccNum;
    /** Maxmimum tickets */
    private int numOfTickets;
    /** Maxmimum annual passes */
    private int maxAnnual;
    /** Maxmimum cycle passes */
    private int maxCycle;
    /** Model for annual pass number selector */
    private SpinnerNumberModel modelAnnual;
    /** Model for cycle pass number selector */
    private SpinnerNumberModel modelCycle;

    /**
     * Constructor
     * 
     * @param areaName     Name of the area
     * @param eventName    Name of the event
     * @param date         Date of the performance
     * @param numOfTickets Number of tickets requested
     * @param perTicket    Price per ticket
     * @param maxAnnual    Max tickets that can be bought with annual pass
     * @param maxCycle    Max tickets that can be bought with cycle pass
     */
    public PaymentSelection(String areaName, String eventName, String date, int numOfTickets, double perTicket,
            int maxAnnual, int maxCycle) {
        super("Pay now");
        this.setTitle("Complete the purchase");

        this.numOfTickets = numOfTickets;
        this.maxCycle = maxCycle;
        this.maxAnnual = maxAnnual;
        numCreditCard = new MyInteger(numOfTickets);
        numAnnual = new MyInteger(0);
        numCycle = new MyInteger(0);

        // Creating buttons
        int butSize = 180;
        buyAnnualPass = new JButton("Purchase annual pass");
        buyAnnualPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyAnnualPass.setMinimumSize(new Dimension(butSize, buyAnnualPass.getPreferredSize().height));
        buyAnnualPass.setMaximumSize(new Dimension(butSize, buyAnnualPass.getPreferredSize().height));

        buyCyclePass = new JButton("Purchase cycle pass");
        buyCyclePass.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyCyclePass.setMinimumSize(new Dimension(butSize, buyCyclePass.getPreferredSize().height));
        buyCyclePass.setMaximumSize(new Dimension(butSize, buyCyclePass.getPreferredSize().height));

        // Creating upper text
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);

        try {
            document.insertString(document.getLength(), "Current Selection:\n\n None", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JTextPane upper = new JTextPane(document);
        upper.setEditable(false);
        upper.setFont(new Font("SansSerif", Font.BOLD, 18));
        upper.setBackground(this.getBackground());
        String txt = "\"" + eventName + "\" on " + date + "\n" + numOfTickets + " tickets selected, of area " + areaName
                + "\n" + perTicket + "€ per ticket\n\nTotal price: " + (numOfTickets * perTicket) + "€";
        upper.setText(txt);
        upper.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Purchase overview"),
                new EmptyBorder(10, 10, 10, 10)));
        upper.setMaximumSize(new Dimension(this.getMaximumSize().width, upper.getPreferredSize().height));
        upper.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Creating bottom left area
        JLabel creditLabel = new JLabel("Tickets to pay with Credit Card: ");
        creditLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        creditLabel.setBackground(this.getBackground());
        JLabel creditNumLabel = new JLabel("" + numCreditCard.value);
        creditNumLabel.setBorder(new EmptyBorder(0, 0, 0, 19));
        ccNum = new TextField("Introduce your Credit Card number");
        ccNum.setFont(creditLabel.getFont());
        ccNum.setAlignmentX(Component.LEFT_ALIGNMENT);

        modelAnnual = new SpinnerNumberModel(numAnnual.value, 0, Math.min(maxAnnual, numOfTickets), 1);
        JSpinner spinnerAnnual = new JSpinner(modelAnnual);
        modelCycle = new SpinnerNumberModel(numCycle.value, 0, Math.min(maxCycle, numOfTickets), 1);
        JSpinner spinnerCycle = new JSpinner(modelCycle);
        spinnerAnnual.setMaximumSize(new Dimension(50, 25));
        spinnerAnnual.setMinimumSize(new Dimension(50, 25));
        spinnerAnnual.setBounds(70, 70, 50, 40);
        spinnerCycle.setMaximumSize(new Dimension(50, 25));
        spinnerCycle.setMinimumSize(new Dimension(50, 25));
        spinnerCycle.setBounds(70, 70, 50, 40);
        creditNumLabel.setFont(spinnerAnnual.getFont());

        spinnerAnnual.addChangeListener(
                spinerListener(spinnerAnnual, numAnnual, spinnerCycle, numCycle, creditLabel, creditNumLabel));

        spinnerCycle.addChangeListener(
                spinerListener(spinnerCycle, numCycle, spinnerAnnual, numAnnual, creditLabel, creditNumLabel));

        JLabel annualText = new JLabel("Tickets to pay with Annual Pass: ", SwingConstants.LEFT);
        annualText.setFont(new Font("SansSerif", Font.PLAIN, 15));
        annualText.setBackground(this.getBackground());

        JPanel annualPanel = new JPanel();
        annualPanel.setLayout(new BoxLayout(annualPanel, BoxLayout.X_AXIS));
        annualPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        annualPanel.add(annualText);
        annualPanel.add(Box.createHorizontalGlue());
        annualPanel.add(spinnerAnnual);
        annualPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cycleText = new JLabel("Tickets to pay with Cycle Pass: ", SwingConstants.LEFT);
        cycleText.setFont(new Font("SansSerif", Font.PLAIN, 15));
        cycleText.setBackground(this.getBackground());

        JPanel cyclePanel = new JPanel();
        cyclePanel.setLayout(new BoxLayout(cyclePanel, BoxLayout.X_AXIS));
        cyclePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        cyclePanel.add(cycleText);
        cyclePanel.add(Box.createHorizontalGlue());
        cyclePanel.add(spinnerCycle);
        cyclePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel creditPanel = new JPanel();
        creditPanel.setLayout(new BoxLayout(creditPanel, BoxLayout.X_AXIS));
        creditPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        creditPanel.add(creditLabel);
        creditPanel.add(Box.createHorizontalGlue());
        creditPanel.add(creditNumLabel);
        creditPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("Choose how you want to pay:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5, 0, 10, 0));

        JPanel sectionLeft = new JPanel();
        sectionLeft.setLayout(new BoxLayout(sectionLeft, BoxLayout.PAGE_AXIS));
        sectionLeft.add(annualPanel);
        sectionLeft.add(Box.createVerticalStrut(5));
        sectionLeft.add(cyclePanel);
        sectionLeft.add(Box.createVerticalStrut(8));
        sectionLeft.add(creditPanel);
        sectionLeft.add(Box.createVerticalStrut(5));
        sectionLeft.add(ccNum);
        ccNum.setMinimumSize(new Dimension(sectionLeft.getPreferredSize().width, 30));
        ccNum.setMaximumSize(new Dimension(sectionLeft.getPreferredSize().width, 30));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.setAlignmentY(Component.TOP_ALIGNMENT);
        left.add(label);
        left.add(sectionLeft);

        // MODIFIED, this now is the right panel
        // Creating bottom mid buttons+label
        label = new JLabel("You don't have a pass yet?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5, 0, 10, 0));

        JPanel buttonsMid = new JPanel();
        buttonsMid.setLayout(new BoxLayout(buttonsMid, BoxLayout.PAGE_AXIS));
        buttonsMid.add(buyAnnualPass);
        buttonsMid.add(Box.createVerticalStrut(5));
        buttonsMid.add(buyCyclePass);

        JPanel mid = new JPanel();
        mid.setLayout(new BoxLayout(mid, BoxLayout.PAGE_AXIS));
        mid.setAlignmentY(Component.TOP_ALIGNMENT);
        mid.add(label);
        mid.add(buttonsMid);

        // Lower section
        JPanel lower = new JPanel();
        lower.setLayout(new GridLayout(1, 3, 0, 80));
        lower.setAlignmentX(Component.LEFT_ALIGNMENT);
        // if (!canReserve)
        lower.add(left);
        lower.add(new JPanel());
        lower.add(mid);
        // lower.add(right);
        lower.setBorder(new EmptyBorder(40, 20, 0, 20));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.setBorder(new EmptyBorder(0, 60, 0, 60));
        center.add(upper);
        center.add(lower);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Sets annual pass action
     * 
     * @param c Listener
     */
    public void setBuyAnnualPassButtonHandler(ActionListener c) {
        this.buyAnnualPass.addActionListener(c);
    }

    /**
     * Sets cycle pass action
     * 
     * @param c Listener
     */
    public void setBuyCyclePassButtonHandler(ActionListener c) {
        this.buyCyclePass.addActionListener(c);
    }

    /**
     * Increments by one the maximim value for annual passes
     */
    public void incrementAnnualPassMaximum() {
        maxAnnual += 1;
        modelAnnual.setMaximum(Integer.valueOf(Math.min(maxAnnual, numOfTickets)));
    }

    /**
     * Increments by one the maximim value for cycle passes
     */
    public void incrementCyclePassMaximum() {
        maxCycle += 1;
        modelCycle.setMaximum(Integer.valueOf(Math.min(maxCycle, numOfTickets)));
    }

    /**
     * Getter for credit card
     * 
     * @return Credit card number
     */
    public String getCreditCardNumber() {
        return ccNum.getText();
    }

    /**
     * Getter for tickets to be paid with annual pass
     * 
     * @return The number
     */
    public int getTicketsAnnualPass() {
        return numAnnual.value;
    }

    /**
     * Getter for tickets to be paid with cycle pass
     * 
     * @return The number
     */
    public int getTicketsCyclePass() {
        return numCycle.value;
    }

    /**
     * Getter for tickets to be paid with credit card
     * 
     * @return The number
     */
    public int getTicketsCreditCard() {
        return numCreditCard.value;
    }

    /**
     * Method to update jspinners and texts accordingly, depending on selection
     * 
     * @param main   First jspinner
     * @param value1 Value associated to first jspinner
     * @param other  Second jspinner
     * @param value2 Value of the second jspinner
     * @param txt    Label to hide/show
     * @param num    Label to hide/show
     * @return Action listener that performs this action
     */
    private ChangeListener spinerListener(JSpinner main, MyInteger value1, JSpinner other, MyInteger value2, JLabel txt,
            JLabel num) {
        return e -> {
            int newVal = (int) main.getValue();
            if (newVal < value1.value) {
                numCreditCard.plus(1);
            } else if (newVal > value1.value) {
                if (numCreditCard.value > 0)
                    numCreditCard.minus(1);
                else
                    value2.minus(1);
            }
            value1.setValue(newVal);
            other.setValue(value2.value);
            num.setText("" + numCreditCard.value);
            Color aux = (numCreditCard.value == 0) ? Color.LIGHT_GRAY : Color.DARK_GRAY;
            txt.setForeground(aux);
            num.setForeground(aux);
            ccNum.setEditable(numCreditCard.value != 0);
        };
    }

    /**
     * Class created just being able to pass an integer to the method
     * spinnerListener and modify the value of that integer
     */
    private class MyInteger {
        /** Value to store */
        private int value;

        /**
         * A default constructor
         * 
         * @param a Integer to pass
         */
        public MyInteger(int a) {
            value = a;
        }

        /**
         * Setter as a value
         * 
         * @param a Number to set
         */
        public void setValue(int a) {
            value = a;
        }

        /**
         * Substacts a certain amount
         * 
         * @param a Amount to substract
         */
        public void minus(int a) {
            value -= a;
        }

        /**
         * Adds a certain amount
         * 
         * @param a Amount to add
         */
        public void plus(int a) {
            value += a;
        }
    }
}
