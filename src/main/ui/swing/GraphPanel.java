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
import java.util.HashMap;

public class GraphPanel extends JPanel implements ActionListener {
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;
    private JComboBox<String> graphchoice;
    private XChartPanel<CategoryChart> display;
    private XChartPanel<PieChart> pieChartDisplay;

    public GraphPanel(ExpenseReport ex, StatisticsReport st) {
        this.expenseReport = ex;
        statisticsReport = st;
        setLayout(new BorderLayout());
        setupButtons();
        showOverallReport();

    }

    private void setupButtons() {
        String[] choices = {"Overall Spending", "Time Graph", "Category Pie Chart"};
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

    private void showTimeReport() {
        String[] categories = new String[] {"This Month", "This Week", "Today"};
        Double[] values = {statisticsReport.averageSpendingMonthly(),
                statisticsReport.averageSpendingWeekly(),
                statisticsReport.averageSpendingToday()};

        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("History of Average Expense")
                .xAxisTitle("Time Frame")
                .yAxisTitle("Average Cost of Expense")
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

    private void showPieChart() {
        HashMap<String, Double> data = new HashMap<>();
        data.put("Food", statisticsReport.totalSpendingByCategory(1));
        data.put("Healthcare", statisticsReport.totalSpendingByCategory(2));
        data.put("Housing", statisticsReport.totalSpendingByCategory(3));
        data.put("Transportation", statisticsReport.totalSpendingByCategory(4));
        data.put("Personal", statisticsReport.totalSpendingByCategory(5));


        PieChart chart = new PieChartBuilder().width(800).height(600)
                .title("Category Pie Chart")
                .build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        for (String key : data.keySet()) {
            chart.addSeries(key, data.get(key));
        }
        pieChartDisplay = new XChartPanel<>(chart);
        removeAll();
        setupButtons();
        add(pieChartDisplay, BorderLayout.NORTH);
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
                case "Time Graph":
                    showTimeReport();
                    break;
                case "Category Pie Chart":
                    showPieChart();
                    break;
                default:
                    break;
            }
        }
    }
}
