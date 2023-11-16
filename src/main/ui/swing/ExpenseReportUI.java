package ui.swing;

import model.ExpenseReport;
import model.StatisticsReport;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExpenseReportUI extends JFrame implements ActionListener {
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
        graph = new GraphPanel(expenseReport, statisticsReport);
        setUpButtons();
        initializeGraphics();
    }

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
    }

    private void loadFile() {
        try {
            expenseReport = jsonReader.read();
            statisticsReport = new StatisticsReport(expenseReport);
            System.out.println("Loaded " + expenseReport.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

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

    private void newExpenseReport() {
        this.expenseReport = setUpExpenseReport();
        this.statisticsReport = new StatisticsReport(expenseReport);
    }

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

    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenseReport);
            jsonWriter.close();
            System.out.println("Saved " + expenseReport.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void quit() {
        dispose();
        System.exit(0);
    }
}
