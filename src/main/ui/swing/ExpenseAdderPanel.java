package ui.swing;

import model.ExpenseReport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Provides a way for users to enter expenses to their expense Report
public class ExpenseAdderPanel extends JPanel implements ActionListener {
    //Accessing the Expense Report
    private ExpenseReport expenseReport;

    // User Input
    private JComboBox<String> categoryInput;
    private JTextField nameInput;
    private JTextField costInput;
    private JTextField descriptionInput;
    private JButton enter;

    // EFFECTS: constructs a new expense adder panel with expenses being added to given expense report
    public ExpenseAdderPanel(ExpenseReport ex) {
        this.expenseReport = ex;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 150));
        setUpLabelsAndInputs();
        setUpButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets up all related buttons
    private void setUpButtons() {
        enter = new JButton("Enter Expense");
        enter.setActionCommand("EnterExpense");
        enter.addActionListener(this);
        add(enter);
    }

    // MODIFIES: this
    // EFFECTS: sets up all labels and text fields
    private void setUpLabelsAndInputs() {
        String[] choices = {"Food", "Healthcare", "Housing",
                "Transportation", "Personal"};
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        categoryPanel.add(new JLabel("Category"));
        categoryPanel.add(categoryInput = new JComboBox<>(choices));
        add(categoryPanel);
        categoryInput.addActionListener(this);
        add(newExpenseParameter("Name       ", nameInput = new JTextField(20)));
        add(newExpenseParameter("Cost       ", costInput = new JTextField(20)));
        add(newExpenseParameter("Description", descriptionInput = new JTextField(20)));
    }

    // EFFECTS: returns each label and text field as a JPanel
    private JPanel newExpenseParameter(String label, JTextField textField) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        rowPanel.add(new JLabel(label));
        rowPanel.add(textField);
        return rowPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and adds the category of expense chosen by user
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("EnterExpense")) {
            String choice = (String) categoryInput.getSelectedItem();
            switch (choice)  {
                case "Food":
                    configureExpense(1);
                    break;
                case "Healthcare":
                    configureExpense(2);
                    break;
                case "Housing":
                    configureExpense(3);
                    break;
                case "Transportation":
                    configureExpense(4);
                    break;
                case "Personal":
                    configureExpense(5);
                    break;
                default:
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the user-provided expense to the expense report
    private void configureExpense(int i) {
        expenseReport.addExpense(nameInput.getText(),
                Double.parseDouble(costInput.getText()),
                descriptionInput.getText(), i);
    }
}