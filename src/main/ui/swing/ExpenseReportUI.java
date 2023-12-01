package ui.swing;

import model.ExpenseReport;
import model.StatisticsReport;
import model.eventlog.EventLog;
import model.eventlog.Event;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the Expense Report App which includes the expense adder,
// displays the table of all expenses, and graphical views of report
// Influenced heavily by:
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class ExpenseReportUI extends JFrame implements ActionListener, WindowListener {
    // setup information
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 800;

    // panels
    private ExpensesPanel ex;
    private ExpenseAdderPanel adder;
    private GraphPanel graph;
    private JPanel persistanceOptions;

    // buttons
    private JButton quit;
    private JButton save;
    private JButton saveAndQuit;

    // accessing the Expense Data
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;
    private String name;
    private double budget;

    // Saving and loading
    private static final String JSON_STORE = "./data/expense.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    //Event Logging
    private EventLog eventLog = EventLog.getInstance();

    // EFFECTS: launches the expense report app
    public ExpenseReportUI() {
        super("Expense Report");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        String[] options = {"Load Saved Report", "Start New Report"};
        if (newFileOrLoad(options) == 0) {
            loadFile();
        } else {
            newExpenseReport();
        }
        ex = new ExpensesPanel(expenseReport);
        adder = new ExpenseAdderPanel(expenseReport);
        graph = new GraphPanel(statisticsReport);
        setUpButtons();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons in app
    private void setUpButtons() {
        quit = new JButton("         Quit       ");
        save = new JButton("        Save       ");
        saveAndQuit = new JButton("Save and Quit");

        quit.setActionCommand("quit");
        save.setActionCommand("save");
        saveAndQuit.setActionCommand("save and quit");

        quit.addActionListener(this);
        save.addActionListener(this);
        saveAndQuit.addActionListener(this);

        persistanceOptions = new JPanel();
        persistanceOptions.setLayout(new BoxLayout(persistanceOptions, BoxLayout.Y_AXIS));
        Component verticalFiller = Box.createVerticalGlue();
        persistanceOptions.add(verticalFiller);
        persistanceOptions.add(quit);
        persistanceOptions.add(save);
        persistanceOptions.add(saveAndQuit);

        addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: loads a save file with saved expense report,
    //          throws an exception if file not found or is empty
    private void loadFile() {
        try {
            expenseReport = jsonReader.read();
            statisticsReport = new StatisticsReport(expenseReport);
        } catch (IOException e) {
           // no action
        } catch (JSONException e) {
            newExpenseReport();
        }
    }

    // EFFECTS: prompts the user to choose between loading in a prior report or starting a new one
    //          returns the user's choice
    private static int newFileOrLoad(String[] options) {
        int choice = JOptionPane.showOptionDialog(null,
                "Do you want to load a save file or start a new session?",
                "Welcome to Expense Report!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,
                options,
                options[1]);
        return choice;
    }

    // MODIFIES: this
    // EFFECTS: constructs a new expense report from scratch
    private void newExpenseReport() {
        this.expenseReport = setUpExpenseReport();
        this.statisticsReport = new StatisticsReport(expenseReport);
    }

    // MODIFIES: this
    // EFFECTS: initializes the expense report with the given name and budget
    private ExpenseReport setUpExpenseReport() {
        name = JOptionPane.showInputDialog("Enter your name:");
        String budgetStr = JOptionPane.showInputDialog("Enter your budget:");
        budget = Double.parseDouble(budgetStr);
        return new ExpenseReport(name, budget);
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel south = new JPanel();
        south.setLayout(new BorderLayout());
        south.add(adder, BorderLayout.WEST);
        south.add(persistanceOptions, BorderLayout.EAST);
        add(ex, BorderLayout.WEST);
        add(graph, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
        setVisible(true);
    }

    // EFFECTS: performs the given action provided by the user
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("quit")) {
            quit();
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("save and quit")) {
            save();
            quit();
        }

    }

    // MODIFIES: this
    // EFFECTS: saves the expense report to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenseReport);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // no action taken
        }
    }

    // EFFECTS: closes the expense report app and prints the EventLog to console
    private void quit() {
        dispose();
        printLog(eventLog);
        System.exit(0);
    }


    // EFFECTS: prints the EventLog to console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: prints the event log when user closes the application
    @Override
    public void windowClosing(WindowEvent e) {
        printLog(eventLog);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
