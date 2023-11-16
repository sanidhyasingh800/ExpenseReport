package ui.swing;

import model.ExpenseReport;
import model.expense.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ExpensesPanel extends JPanel implements ActionListener {

    // table related fields
    private DefaultTableModel model;
    private JTable table;

    // Buttons
    private JButton refresh = new JButton("Refresh");
    private JButton delete = new JButton("Delete");

    // Accessing the expenseReport
    private ExpenseReport expenseReport;
    private static int refreshCheck;

    public ExpensesPanel(ExpenseReport expenseReport) {
        super();
        setUpExpenseReport(expenseReport);
        setBackground(Color.white);
        setLayout(new BorderLayout());
        setUpTable();
        setUpButtons();

    }

    private void setUpExpenseReport(ExpenseReport expenseReport) {
        this.expenseReport = expenseReport;
        refreshCheck = 0;
    }

    private void setUpButtons() {
        JPanel buttons = new JPanel(new FlowLayout());
        refresh.setActionCommand("refresh");
        refresh.addActionListener(this);
        buttons.add(refresh);
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        buttons.add(delete);
        add(buttons, BorderLayout.SOUTH);
    }

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
    }

    public void displayExpenses() {
        if (expenseReport.getExpenses().size() != refreshCheck) {
            model.setRowCount(0);
            refreshCheck = expenseReport.getExpenses().size();
            for (Expense e: expenseReport.getExpenses()) {
                String[] data = returnDisplayData(e);
                model.addRow(data);
            }
        }

    }

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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("refresh")) {
            displayExpenses();
        } else if (e.getActionCommand().equals("delete")) {
            deleteRow();
        }
    }

    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            refreshCheck--;
            model.removeRow(row);
            expenseReport.removeExpense(row);
        }
    }
}
