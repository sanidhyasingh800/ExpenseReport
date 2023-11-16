package ui.swing;

import model.ExpenseReport;
import model.StatisticsReport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseReportUI extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private ExpensesPanel ex;
    private ExpenseAdderPanel adder;
    private GraphPanel graph;
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;

    public ExpenseReportUI(ExpenseReport exp) {
        super("Expense Report");
        this.expenseReport = exp;
        this.statisticsReport = new StatisticsReport(expenseReport);
        ex = new ExpensesPanel(expenseReport);
        adder = new ExpenseAdderPanel(expenseReport);
        graph = new GraphPanel(expenseReport, statisticsReport);
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        add(ex, BorderLayout.WEST);
        add(adder, BorderLayout.SOUTH);
        add(graph, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
