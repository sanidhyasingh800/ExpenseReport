package ui.swing;


import model.StatisticsReport;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

// Displays graphical information about expense report and provides users the choice to view
// different statistics about their expenses.
// Design uses the XChart Library which provides a way to create graphs in Java Swing
// https://knowm.org/open-source/xchart/
public class GraphPanel extends JPanel implements ActionListener {
    private StatisticsReport statisticsReport;
    private JComboBox<String> graphChoice;
    private XChartPanel<CategoryChart> display;
    private XChartPanel<PieChart> pieChartDisplay;

    // EFFECTS: constructs a graph panel with provided statistics report
    public GraphPanel(StatisticsReport st) {
        statisticsReport = st;
        setLayout(new BorderLayout());
        setupButtons();
        showOverallReport();

    }

    // MODIFIES: this
    // EFFECTS: initializes and sets up all related buttons
    private void setupButtons() {
        String[] choices = {"Overall Spending", "Time Graph", "Category Pie Chart"};
        graphChoice = new JComboBox<>(choices);
        graphChoice.setActionCommand("Choose graph");
        graphChoice.addActionListener(this);
        add(graphChoice, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: shows a bar graph of the overall spending per category
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

    // MODIFIES: this
    // EFFECTS: shows a bar graph of the average expense cost over month, week, and day
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

    // MODIFIES: this
    // EFFECTS: shows a pie chart of the percentage of expenses in each category
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

    // EFFECTS: shows the graph selected by the user
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Choose graph")) {
            String choice = (String) graphChoice.getSelectedItem();
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
