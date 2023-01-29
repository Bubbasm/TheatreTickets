package view.manager.addEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import view.shared.SpringUtilities;
import view.shared.TemplatePanel;
import view.shared.TextField;

/**
 * View for adding a new event (part 1/2)
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class AddEventFirst extends TemplatePanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;

    /** Name for the event */
    private TextField eventName;
    /** Title for the event */
    private TextField eventTitle;
    /** Duration */
    private JSpinner duration;
    /** Description */
    private JTextArea description;
    /** Author */
    private TextField author;
    /** Director */
    private TextField director;
    /** Capacity restriction from 0 to 100 */
    private JSpinner capRestr;
    /** Category of event (Theatre, Music and Dance) */
    private JComboBox<String> category;

    /** Panel to display music event specific attributes */
    private JPanel musicPanel;
    /** Orchestra playing */
    private TextField musicOrchestra;
    /** Soloists playing */
    private TextField soloist;
    /** Description for the program to be followed */
    private JTextArea program;

    /** Panel to display theatre event specific attributes */
    private JPanel theatrePanel;
    /** Actors participating */
    private JTextArea actors;

    /** Panel to display dance event specific attributes */
    private JPanel dancePanel;
    /** Orchestra playing */
    private TextField danceOrchestra;
    /** Conductor for the dance */
    private TextField conductor;
    /** Dancers participating */
    private JTextArea dancers;

    /**
     * Constructor for the view
     */
    public AddEventFirst() {
        super("Next step");
        this.setTitle("New Event (1/2)");

        // Creating left pannel
        JPanel leftPanel = new JPanel(new SpringLayout());
        leftPanel.setBorder(new EmptyBorder(0, 40, 0, 40));

        leftPanel.add(Box.createRigidArea(new Dimension(10, 40)));
        leftPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        JLabel l = new JLabel("Event:", JLabel.TRAILING);
        eventName = new TextField("Event name");
        l.setLabelFor(eventName);
        leftPanel.add(l);
        leftPanel.add(eventName);

        l = new JLabel("Title:", JLabel.TRAILING);
        eventTitle = new TextField("Event title");
        l.setLabelFor(eventTitle);
        leftPanel.add(l);
        leftPanel.add(eventTitle);

        l = new JLabel("Duration: (minutes)", JLabel.TRAILING);
        duration = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        // !spinner.addChangeListener(e -> numberOfSeats = (int)spinner.getValue());
        duration.setMinimumSize(new Dimension(60, 25));
        duration.setMaximumSize(new Dimension(60, 25));
        duration.setPreferredSize(new Dimension(60, 25));
        l.setLabelFor(duration);
        leftPanel.add(l);
        leftPanel.add(duration);

        l = new JLabel("Description:", JLabel.TRAILING);
        description = new JTextArea();
        description.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 5, 5, 5)));
        description.setMinimumSize(new Dimension(description.getPreferredSize().width, 100));
        description.setMaximumSize(new Dimension(description.getPreferredSize().width, 100));
        description.setPreferredSize(new Dimension(description.getPreferredSize().width, 100));
        description.setLineWrap(true);
        l.setLabelFor(description);
        leftPanel.add(l);
        leftPanel.add(description);

        l = new JLabel("Author:", JLabel.TRAILING);
        author = new TextField("Author");
        l.setLabelFor(author);
        leftPanel.add(l);
        leftPanel.add(author);

        l = new JLabel("Director:", JLabel.TRAILING);
        director = new TextField("Director");
        l.setLabelFor(director);
        leftPanel.add(l);
        leftPanel.add(director);

        l = new JLabel("Capacity restriction: (%)", JLabel.TRAILING);
        capRestr = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        // !spinner.addChangeListener(e -> numberOfSeats = (int)spinner.getValue());
        capRestr.setMinimumSize(new Dimension(60, 25));
        capRestr.setMaximumSize(new Dimension(60, 25));
        capRestr.setPreferredSize(new Dimension(60, 25));
        l.setLabelFor(capRestr);
        leftPanel.add(l);
        leftPanel.add(capRestr);

        SpringUtilities.makeCompactGrid(leftPanel, 8, 2, // rows, cols
                0, 0, // initX, initY
                10, 10, // xPad, yPad
                60, List.of(3, 7)); // custom width for some components

        // Creating right pannel
        JPanel topRight = new JPanel(new SpringLayout());
        topRight.setBorder(new EmptyBorder(0, 40, 15, 40));

        l = new JLabel("Category:", JLabel.TRAILING);
        category = new JComboBox<>();
        category.addItem("MUSIC");
        category.addItem("DANCE");
        category.addItem("THEATRE");
        category.setMinimumSize(new Dimension(100, 25));
        category.setMaximumSize(new Dimension(100, 25));
        category.setPreferredSize(new Dimension(100, 25));
        l.setLabelFor(category);
        topRight.add(l);
        topRight.add(category);

        SpringUtilities.makeCompactGrid(topRight, 1, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);

        // creating panel for type == MUSIC
        musicPanel = new JPanel(new SpringLayout());

        l = new JLabel("Orchestra:", JLabel.TRAILING);
        musicOrchestra = new TextField("Orchestra");
        l.setLabelFor(musicOrchestra);
        musicPanel.add(l);
        musicPanel.add(musicOrchestra);

        l = new JLabel("Soloist:", JLabel.TRAILING);
        soloist = new TextField("Soloist");
        l.setLabelFor(soloist);
        musicPanel.add(l);
        musicPanel.add(soloist);

        l = new JLabel("Program:", JLabel.TRAILING);
        program = new JTextArea();
        program.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 5, 5, 5)));
        program.setMinimumSize(new Dimension(program.getPreferredSize().width, 100));
        program.setMaximumSize(new Dimension(program.getPreferredSize().width, 100));
        program.setPreferredSize(new Dimension(program.getPreferredSize().width, 100));
        program.setLineWrap(true);
        l.setLabelFor(program);
        musicPanel.add(l);
        musicPanel.add(program);

        SpringUtilities.makeCompactGrid(musicPanel, 3, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);

        // creating panel for type == DANCE
        dancePanel = new JPanel(new SpringLayout());

        l = new JLabel("Orchestra:", JLabel.TRAILING);
        danceOrchestra = new TextField("Orchestra");
        l.setLabelFor(danceOrchestra);
        dancePanel.add(l);
        dancePanel.add(danceOrchestra);

        l = new JLabel("Conductor:", JLabel.TRAILING);
        conductor = new TextField("Conductor");
        l.setLabelFor(conductor);
        dancePanel.add(l);
        dancePanel.add(conductor);

        l = new JLabel("Dancers:", JLabel.TRAILING);
        dancers = new JTextArea();
        dancers.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 5, 5, 5)));
        dancers.setMinimumSize(new Dimension(dancers.getPreferredSize().width, 100));
        dancers.setMaximumSize(new Dimension(dancers.getPreferredSize().width, 100));
        dancers.setPreferredSize(new Dimension(dancers.getPreferredSize().width, 100));
        dancers.setLineWrap(true);
        l.setLabelFor(dancers);
        dancePanel.add(l);
        dancePanel.add(dancers);

        l = new JLabel("Add the dancers separating them with ';'", JLabel.CENTER);
        l.setPreferredSize(new Dimension(dancers.getPreferredSize().width, 25));
        l.setFont(l.getFont().deriveFont(Font.PLAIN, 15F));
        l.setForeground(Color.GRAY);
        dancePanel.add(Box.createRigidArea(new Dimension(10, 25)));
        dancePanel.add(l);

        SpringUtilities.makeCompactGrid(dancePanel, 4, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);

        // creating panel for type == THEATRE
        theatrePanel = new JPanel(new SpringLayout());

        l = new JLabel("Actors:", JLabel.TRAILING);
        actors = new JTextArea();
        actors.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 5, 5, 5)));
        actors.setMinimumSize(new Dimension(actors.getPreferredSize().width, 100));
        actors.setMaximumSize(new Dimension(actors.getPreferredSize().width, 100));
        actors.setPreferredSize(new Dimension(actors.getPreferredSize().width, 100));
        actors.setLineWrap(true);
        l.setLabelFor(actors);
        theatrePanel.add(l);
        theatrePanel.add(actors);

        l = new JLabel("Add the actors separating them with ';'", JLabel.CENTER);
        l.setPreferredSize(new Dimension(dancers.getPreferredSize().width, 25));
        l.setFont(l.getFont().deriveFont(Font.PLAIN, 15F));
        l.setForeground(Color.GRAY);
        theatrePanel.add(Box.createRigidArea(new Dimension(10, 25)));
        theatrePanel.add(l);

        SpringUtilities.makeCompactGrid(theatrePanel, 2, 2, // rows, cols
                0, 0, // initX, initY
                10, 10);

        JPanel botRight = new JPanel(new BorderLayout());
        botRight.setBorder(new EmptyBorder(0, 40, 0, 40));
        botRight.add(musicPanel, BorderLayout.CENTER);

        category.addActionListener(e -> {
            if (AddEventFirst.this.getCategory().equals("MUSIC")) {
                botRight.removeAll();
                botRight.add(musicPanel, BorderLayout.CENTER);
            } else if (AddEventFirst.this.getCategory().equals("DANCE")) {
                botRight.removeAll();
                botRight.add(dancePanel, BorderLayout.CENTER);
            } else {
                botRight.removeAll();
                botRight.add(theatrePanel, BorderLayout.CENTER);
            }
            botRight.revalidate();
            botRight.repaint();
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(new EmptyBorder(0, 10, 0, 40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(topRight);
        rightPanel.add(botRight);

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.add(leftPanel);
        center.add(rightPanel);

        this.add(center, BorderLayout.CENTER);
    }

    /**
     * Getter for the event title
     * 
     * @return the title
     */
    public String getEventTitle() {
        return eventTitle.getText();
    }

    /**
     * Getter for the event name
     * 
     * @return the name
     */
    public String getEventName() {
        return eventName.getText();
    }

    /**
     * Getter for the event duration
     * 
     * @return the duration in minutes
     */
    public int getDuration() {
        return (int) duration.getValue();
    }

    /**
     * Getter for the event description
     * 
     * @return the description
     */
    public String getDescription() {
        return description.getText();
    }

    /**
     * Getter for the event author
     * 
     * @return the author
     */
    public String getAuthor() {
        return author.getText();
    }

    /**
     * Getter for the event director
     * 
     * @return the director
     */
    public String getDirector() {
        return director.getText();
    }

    /**
     * Getter for the event capacity restriction
     * 
     * @return the restriction between 0 and 1
     */
    public double getRestriction() {
        return ((int) capRestr.getValue()) / 100.0;
    }

    /**
     * Getter for the event category
     * 
     * @return a string with the category (dance, music, ...)
     */
    public String getCategory() {
        return (String) category.getSelectedItem();
    }

    /**
     * Getter for the event orchestra
     * 
     * @return the orchestra
     */
    public String getOrchestra() {
        String str = getCategory();
        if (str.equals("THEATRE"))
            return null;
        else if (str.equals("MUSIC"))
            return musicOrchestra.getText();
        return danceOrchestra.getText();
    }

    /**
     * Getter for the event soloist (for music events)
     * 
     * @return the soloist
     */
    public String getSoloist() {
        return soloist.getText();
    }

    /**
     * Getter for the event program (for music events)
     * 
     * @return the program
     */
    public String getProgram() {
        return program.getText();
    }

    /**
     * Getter for the event conductor (for music events)
     * 
     * @return the conductor
     */
    public String getConductor() {
        return conductor.getText();
    }

    /**
     * Getter for the event actors (for theatre events)
     * 
     * @return a list with the actors
     */
    public List<String> getActors() {
        if (actors.getText().equals(""))
            return null;
        return Arrays.asList(actors.getText().split(";"));
    }

    /**
     * Getter for the event dancers (for dance events)
     * 
     * @return the dancers
     */
    public List<String> getDancers() {
        if (dancers.getText().equals(""))
            return null;
        return Arrays.asList(dancers.getText().split(";"));
    }

    /** Clears all fields of all text areas */
    public void clearFields() {
        eventTitle.clearText();
        eventName.clearText();
        duration.setValue(0);
        description.setText("");
        author.clearText();
        director.clearText();
        capRestr.setValue(0);

        musicOrchestra.clearText();
        soloist.clearText();
        program.setText("");

        actors.setText("");

        danceOrchestra.clearText();
        conductor.clearText();
        dancers.setText("");
    }

}
