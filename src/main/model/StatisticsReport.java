package model;

import model.expense.Expense;

// Provides information about the statistics, total spending, average spending
// and more for an Expense Report. Provides information of expenses tracked over
// a day, week, and month, Does more than just store and return expenses
// like ExpenseReport
public class StatisticsReport {
    private final ExpenseReport expenseReport;

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

    //EFFECTS: returns the percentage of all money spent in given category, 0 if no expenses
    public double percentageOfCategory(int category) {
        if (totalSpending() == 0) {
            return 0;
        }
        return 100 * totalSpendingByCategory(category) / totalSpending();
    }

    // EFFECTS: returns the average cost of the entire expense report, 0 if no expenses
    public double averageCost() {
        return totalSpending() / expenseReport.getExpenses().size();
    }

    // EFFECTS: returns the average cost of an expense within given category, 0 if none
    public double averageCostByCategory(int category) {
        if (expenseReport.getSpecificCategoryOfExpense(category).size() == 0) {
            return 0;
        }
        return totalSpendingByCategory(category)
                / expenseReport.getSpecificCategoryOfExpense(category).size();
    }

    //EFFECTS: returns the total number of expenses, 0 if none
    public int totalExpenses() {
        return expenseReport.getExpenses().size();
    }

    //EFFECTS: returns the number of expenses within given category, 0 if none
    public int totalExpensesInCategory(int category) {
        return expenseReport.getSpecificCategoryOfExpense(category).size();
    }

    // Statistics based on time:

    //EFFECTS: returns total number of expenses today, 0 if none
    public int totalExpensesToday() {
        return expenseReport.filterByDay().size();
    }

    //EFFECTS: returns total number of expenses of last 7 days, 0 if none
    public int totalExpensesWeekly() {
        return expenseReport.filterByWeek().size();
    }

    // EFFECTS: returns total number of expenses this month, 0 if none
    public int totalExpensesMonthly() {
        return expenseReport.filterByMonth().size();
    }

    //EFFECTS: returns total cost of expenses today, 0 if no expenses
    public double totalSpendingToday() {
        double total = 0;
        for (Expense e :  expenseReport.filterByDay()) {
            total += e.getAmount();
        }
        return total;
    }

    //EFFECTS: returns total cost of expenses this week, 0 if no expenses
    public double totalSpendingWeekly() {
        double total = 0;
        for (Expense e :  expenseReport.filterByWeek()) {
            total += e.getAmount();
        }
        return total;
    }

    //EFFECTS: returns total cost of expenses this month, 0 if no expenses
    public double totalSpendingMonthly() {
        double total = 0;
        for (Expense e :  expenseReport.filterByMonth()) {
            total += e.getAmount();
        }
        return total;
    }

    // EFFECTS: returns the average cost of an expense today, 0 if no expense
    public double averageSpendingToday() {
        if (totalExpensesToday() == 0) {
            return 0;
        }
        return totalSpendingToday() / totalExpensesToday();
    }

    // EFFECTS: returns the average cost of an expense of last 7 days, 0 if no expense
    public double averageSpendingWeekly() {
        if (totalExpensesWeekly() == 0) {
            return 0;
        }
        return totalSpendingWeekly() / totalExpensesWeekly();
    }

    // EFFECTS: returns the average cost of an expense this month, 0 if no expense
    public double averageSpendingMonthly() {
        if (totalSpendingMonthly() == 0) {
            return 0;
        }
        return totalSpendingMonthly() / totalExpensesMonthly();
    }

}
