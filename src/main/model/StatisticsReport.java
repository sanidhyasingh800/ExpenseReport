package model;

import model.expense.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Provides information about the statistics, total spending, average spending
// and more for an Expense Report. Provides information of expenses tracked over
// a day, week, and month. Provides personalized statistics for each type of expense as well.
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
    // todo: test
    public double averageCost() {
        if (expenseReport.getExpenses().size() == 0) {
            return 0;
        }
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

    // EFFECTS: returns total number of expenses of last 30 days, 0 if none
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

    //EFFECTS: returns total cost of expenses of last 7 days, 0 if no expenses
    public double totalSpendingWeekly() {
        double total = 0;
        for (Expense e :  expenseReport.filterByWeek()) {
            total += e.getAmount();
        }
        return total;
    }

    //EFFECTS: returns total cost of expenses of last 30 days, 0 if no expenses
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

    // Personalized Statistics for each type of expense

    // Food:
    // todo: test
    // EFFECTS: returns the average cost of a food item bought overall in all Food Expenses
    public double getAverageAmountPerFoodItem() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(1);
        int totalItems = 0;
        double amountSpent = 0;
        if (list.size() == 0) {
            return 0;
        }
        for (Expense ex: list) {
            totalItems += ((FoodExpense) ex).getFoodItems().size();
            amountSpent += ex.getAmount();
        }
        return amountSpent / totalItems;
    }

    // Healthcare:

    // EFFECTS: returns the total amount saved due to Insurance
    public double totalAmountSaved() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(2);
        double total = 0;
        for (Expense ex : list) {
            total += ((HealthcareExpense) ex).getAmountCovered();
        }
        return total;
    }

    //EFFECTS: returns the percentage of healthcare expenses covered by Insurance
    public double percentageCoveredByInsurance() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(2);
        double total = 0;
        for (Expense ex : list) {
            if (((HealthcareExpense) ex).getAmountCovered() > 0) {
                total++;
            }
        }
        return 100 * total / list.size();
    }

    // Housing:

    //EFFECTS: returns a list of double of length 5 with
    //         0th index storing total spent on water bills
    //         1st index storing total spent on electricity bills
    //         2nd index storing total spent on trash Bills
    //         3rd index storing total spent on internet Bills
    //         4th index storing total spent on rent or mortgage
    public List<Double> getBillsSummary() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(3);
        List<Double> returnList = new ArrayList<>(Collections.nCopies(5, 0.0));
        for (Expense ex: list) {
            if (((HousingExpense) ex).getTypeofBill() != 0) {
                returnList.set(((HousingExpense) ex).getTypeofBill() - 1, ex.getAmount());
            }
        }
        return returnList;
    }

    // Transportation

    //EFFECTS: returns the total amount spent on Green Methods of Transportation
    public double totalSpentOnGreenEnergy() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(4);
        double total = 0;
        for (Expense ex : list) {
            if (((TransportationExpense) ex).getTypeOfTransportation() == 1) {
                total += ex.getAmount();
            }
        }
        return total;
    }

    //EFFECTS: returns the total amount spent on Green Methods of Transportation
    public double totalSpentOnPublicTransportation() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(4);
        double total = 0;
        for (Expense ex : list) {
            if (((TransportationExpense) ex).getTypeOfTransportation() == 2) {
                total += ex.getAmount();
            }
        }
        return total;
    }

    //EFFECTS: returns the total amount spent on Green Methods of Transportation
    public double totalSpentOnPersonalTransportation() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(4);
        double total = 0;
        for (Expense ex : list) {
            if (((TransportationExpense) ex).getTypeOfTransportation() == 3) {
                total += ex.getAmount();
            }
        }
        return total;
    }

    // Personal:

    // EFFECTS: returns the amount spent of personal expenses on needs
    public double totalAmountOnNeeds() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(5);
        double total = 0;
        for (Expense ex : list) {
            if (((PersonalExpense) ex).isNeed()) {
                total += ex.getAmount();
            }
        }
        return total;
    }

    // EFFECTS: returns the amount spent of personal expenses on wants
    public double totalAmountOnWants() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(5);
        double total = 0;
        for (Expense ex : list) {
            if (!((PersonalExpense) ex).isNeed()) {
                total += ex.getAmount();
            }
        }
        return total;
    }

    // getters

    public ExpenseReport getExpenseReport() {
        return expenseReport;
    }




}
