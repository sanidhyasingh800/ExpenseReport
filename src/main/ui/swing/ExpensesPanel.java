package ui.swing;

import model.ExpenseReport;
import model.expense.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


// Table Panel for displaying all expenses as well as filtered views based on category and time
public class ExpensesPanel extends JPanel implements ActionListener {

    // table related fields
    private DefaultTableModel model;
    private JTable table;

    // Buttons
    private JButton refresh = new JButton("Refresh");
    private JButton delete = new JButton("Delete");
    private JComboBox<String> filter;

    // Accessing the expenseReport
    private ExpenseReport expenseReport;
    private static int refreshCheck;
    private static boolean currentlyDisplayingFilter;

    // EFFECTS: constructs a new Expenses Panel with the given expenseReport
    //          and sets up all corresponding buttons
    public ExpensesPanel(ExpenseReport expenseReport) {
        super();
        setUpExpenseReport(expenseReport);
        setBackground(Color.white);
        setLayout(new BorderLayout());
        setUpTable();
        setUpButtons();

    }

    // MODIFIES: this
    // EFFECTS: connects the expense report to the expenses panel
    private void setUpExpenseReport(ExpenseReport expenseReport) {
        this.expenseReport = expenseReport;
        refreshCheck = 0;
        currentlyDisplayingFilter = false;
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons in the expenses panel
    private void setUpButtons() {
        JPanel buttons = new JPanel(new FlowLayout());
        refresh.setActionCommand("refresh");
        refresh.addActionListener(this);
        buttons.add(refresh);
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        buttons.add(delete);
        String[] choices = {"Overall Report",
                            "Food Expenses",
                            "Healthcare Expenses",
                            "Housing Expenses",
                            "Transportation Expenses",
                            "Personal Expenses", "Expenses Made Today", "Expenses Made This Week",
                            "Expenses Made This Month"};
        filter = new JComboBox<>(choices);
        buttons.add(filter);
        add(buttons, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up the table with given categories
    //          and initializes it to show overall expenses
    private void setUpTable() {
        String[] columns = new String[4];
        columns[0] = "Category";
        columns[1] = "Name";
        columns[2] = "Amount";
        columns[3] = "Description";
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(600, 600));
        JScrollPane table2 = new JScrollPane(table);
        add(table2, BorderLayout.NORTH);
        displayExpenses();
    }

    // MODIFIES: this
    // EFFECTS: displays all current expenses in report
    public void displayExpenses() {
        expenseReport.setAccessedThroughGraph(true);
        if (expenseReport.getExpenses().size() != refreshCheck || currentlyDisplayingFilter) {
            model.setRowCount(0);
            currentlyDisplayingFilter = false;
            delete.setEnabled(true);
            refreshCheck = expenseReport.getExpenses().size();
            expenseReport.setAccessedThroughGraph(false);
            List<Expense> expense = expenseReport.getExpenses();
            for (Expense e: expense) {
                String[] data = returnDisplayData(e);
                model.addRow(data);
            }
        }

    }

    // EFFECTS: returns the correct display representation of each type of expense
    private String[] returnDisplayData(Expense e) {
        String[] data;
        if (e instanceof FoodExpense) {
            data = new String[]{"Food", e.getName(), Double.toString(e.getAmount()), e.getDescription()};
        } else if (e instanceof HealthcareExpense) {
            data = new String[]{"Healthcare", e.getName(), Double.toString(e.getAmount()), e.getDescription()};
        } else if (e instanceof HousingExpense) {
            data = new String[]{"Housing", e.getName(), Double.toString(e.getAmount()), e.getDescription()};
        } else if (e instanceof TransportationExpense) {
            data = new String[]{"Transportation", e.getName(), Double.toString(e.getAmount()), e.getDescription()};
        } else if (e instanceof PersonalExpense) {
            data = new String[]{"Personal", e.getName(), Double.toString(e.getAmount()), e.getDescription()};
        } else {
            data = new String[4];
        }
        return data;
    }


    // EFFECTS: performs the action chosen by button pressed by user
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("refresh")) {
            displaySelectedTable();
        } else if (e.getActionCommand().equals("delete")) {
            deleteRow();
        }
    }

    // EFFECTS: displays the correct filter on the table as per user input
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displaySelectedTable() {
        String choice = ((String) filter.getSelectedItem());
        switch (choice) {
            case "Overall Report":
                displayExpenses();
                break;
            case "Food Expenses":
                displayList(expenseReport.getSpecificCategoryOfExpense(1));
                break;
            case "Healthcare Expenses":
                displayList(expenseReport.getSpecificCategoryOfExpense(2));
                break;
            case "Housing Expenses":
                displayList(expenseReport.getSpecificCategoryOfExpense(3));
                break;
            case "Transportation Expenses":
                displayList(expenseReport.getSpecificCategoryOfExpense(4));
                break;
            case "Personal Expenses":
                displayList(expenseReport.getSpecificCategoryOfExpense(5));
                break;
            case "Expenses Made Today":
                displayList(expenseReport.filterByDay());
                break;
            case "Expenses Made This Month":
                displayList(expenseReport.filterByMonth());
                break;
            case "Expenses Made This Week":
                displayList(expenseReport.filterByWeek());
                break;
            default:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the list in the table
    private void displayList(List<Expense> list) {
        model.setRowCount(0);
        currentlyDisplayingFilter = true;
        delete.setEnabled(false);
        for (Expense e: list) {
            String[] data = returnDisplayData(e);
            model.addRow(data);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the expense chosen by the user
    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            refreshCheck--;
            model.removeRow(row);
            expenseReport.removeExpense(row);
        }
    }
}
