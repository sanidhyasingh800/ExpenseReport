package model;

import model.Expense.Expense;

// Provides information about the statistics, total spending, average spending
// and more for an Expense Report. Does more than just store and return expenses
// like ExpenseReport
public class StatisticsReport {
    private ExpenseReport expenseReport;

    public StatisticsReport(ExpenseReport expenseReport) {
        this.expenseReport = expenseReport;
    }

    //EFFECTS: returns the total spending for an expense report
    //         0 if no expenses
    public double totalSpending() {
        double total = 0;
        for (Expense ex: expenseReport.getExpenses()) {
            total += ex.getAmount();
        }
        return total;
    }

    //EFFECTS: returns the budget left after all spending
    public double budgetLeft() {
        return expenseReport.getBudget() - totalSpending();
    }

    //EFFECTS: returns the total spending for a specific category
    //         0 if no expenses
    public double totalSpendingByCategory(int category) {
        double total = 0;
        for (Expense ex: expenseReport.getSpecificCategoryOfExpense(category)) {
            total += ex.getAmount();
        }
        return total;
    }

    //EFFECTS: returns the percentage of all money spent in given category
    public double percentageOfCategory(int category) {
        if (totalSpending() == 0) {
            return 0;
        }
        return 100 * totalSpendingByCategory(category) / totalSpending();
    }

    // EFFECTS: returns the average cost of the entire expense report;
    public double averageCost() {
        return totalSpending() / expenseReport.getExpenses().size();
    }

    // EFFECTS: returns the average cost of an expense within given category
    public double averageCostByCategory(int category) {
        if (expenseReport.getSpecificCategoryOfExpense(category).size() == 0) {
            return 0;
        }
        return totalSpendingByCategory(category)
                / expenseReport.getSpecificCategoryOfExpense(category).size();
    }

    //EFFECTS: returns the total number of expenses
    public int totalExpenses() {
        return expenseReport.getExpenses().size();
    }

    //EFFECTS: returns the number of expenses within given category
    public int totalExpensesInCategory(int category) {
        return expenseReport.getSpecificCategoryOfExpense(category).size();
    }


}
