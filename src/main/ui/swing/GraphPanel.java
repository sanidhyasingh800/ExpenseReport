package ui.swing;

import model.ExpenseReport;
import model.StatisticsReport;
import model.expense.Expense;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GraphPanel extends JPanel implements ActionListener {
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;
    private JComboBox<String> graphchoice;
    private XChartPanel<CategoryChart> display;

    public GraphPanel(ExpenseReport ex, StatisticsReport st) {
        this.expenseReport = ex;
        statisticsReport = st;
        setLayout(new BorderLayout());
        setupButtons();

    }

    private void setupButtons() {
        String[] choices = {"Overall Spending"};
        graphchoice = new JComboBox<>(choices);
        graphchoice.setActionCommand("Choose graph");
        graphchoice.addActionListener(this);
        add(graphchoice, BorderLayout.SOUTH);
    }

    private void showOverallReport() {
        String[] categories = new String[] {"Food", "Healthcare", "Housing",
                "Transportation", "Personal"};
        Double[] values = {statisticsReport.totalSpendingByCategory(1),
                statisticsReport.totalSpendingByCategory(2),
                statisticsReport.totalSpendingByCategory(3),
                statisticsReport.totalSpendingByCategory(4),
                statisticsReport.totalSpendingByCategory(5)};

        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("Distribution of Expenses Across Categories")
                .xAxisTitle("Categories")
                .yAxisTitle("Expenses (in $)")
                .build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.addSeries("Overall Report", Arrays.asList(categories), Arrays.asList(values));
        display = new XChartPanel<>(chart);
        removeAll();
        setupButtons();
        add(display, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Choose graph")) {
            String choice = (String) graphchoice.getSelectedItem();
            switch (choice) {
                case "Overall Spending":
                    showOverallReport();
                    break;
                default:
                    break;
            }
        }
    }
}
