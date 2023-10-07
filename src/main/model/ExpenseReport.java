package model;

import java.util.ArrayList;
import java.util.List;

// Represents an Expense Report that allows you to add and remove Expenses
// and provides functionality to return all expenses, filter by category, and by cost
public class ExpenseReport {
    private List<Expense> expenseList;
    private double budget;

    // REQUIRES: budget > 0
    // EFFECTS: constructs a new Expense Report with the given budget
    //          and no expenses added
    public ExpenseReport(double budget) {
        expenseList = new ArrayList<Expense>();
        this.budget = budget;
    }

    // REQUIRES: amount >= 0 and category is one of (1,2,3,4,5)
    // MODIFIES: this
    // EFFECTS: adds a new expense with given name, amount, and description to List of Expenses
    //          category = 1 adds a new Food Expense
    //          category = 2 adds a new Healthcare Expense
    //          category = 3 adds a new Housing Expense
    //          category = 4 adds a new Transportation Expense
    //          category = 5 adds a new Personal Expense
    public void addExpense(String name, double amount, String description, int category) {
        switch (category) {
            case 1:
                expenseList.add(new FoodExpense(name, amount, description));
                break;
            case 2:
                expenseList.add(new HealthcareExpense(name, amount, description));
                break;
            case 3:
                expenseList.add(new HousingExpense(name, amount, description));
                break;
            case 4:
                expenseList.add(new TransportationExpense(name, amount, description));
                break;
            case 5:
                expenseList.add(new PersonalExpense(name, amount, description));
                break;
        }
    }

    // todo: implement RemoveExpense but how???
    //REQUIRES: removeExpense must already be in the List of Expenses
    //MODIFIES: this
    //EFFECTS: removes removeExpense from List of Expenses
    public void removeExpense(Expense removeExpense) {
    }

    //REQUIRES: amount>=0
    //EFFECTS: returns all expenses with cost of expense >= amount
    public List<Expense> getExpensesAboveAmount(double amount) {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex: expenseList) {
            if (ex.getAmount() >= amount) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //REQUIRES: amount >=0
    //EFFECTS: returns all expenses with cost of expense <= amount
    public List<Expense> getExpensesBelowAmount(int amount) {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex: expenseList) {
            if (ex.getAmount() <= amount) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns a list of expenses of specified category
    //         (see addExpense to see which values of category correspond to which type)
    public List<Expense> getSpecificCategoryOfExpense(int category) {
        List<Expense> returnList;
        switch (category) {
            case 1:
                returnList = getFoodExpenses();
                break;
            case 2:
                returnList = getHealthExpenses();
                break;
            case 3:
                returnList = getHousingExpenses();
                break;
            case 4:
                returnList = getTransportationExpenses();
                break;
            case 5:
                returnList = getPersonalExpenses();
                break;
            default:
                returnList = new ArrayList<>();
        }
        return returnList;
    }

    //MODIFIES: this
    //EFFECTS: changes the budget of the expense report to newBudget
    public void changeBudget(double newBudget) {
        this.budget = newBudget;
    }

    // Getters:

    public List<Expense> getExpenses() {
        return null;
    }

    public double getBudget() {
        return budget;
    }


    // Private Helper Methods

    //EFFECTS: returns all Food Expenses
    private List<Expense> getFoodExpenses() {
        List<Expense> returnList = new ArrayList<Expense>();
        for (Expense ex : expenseList) {
            if (ex instanceof FoodExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Healthcare Expenses
    private List<Expense> getHealthExpenses() {
        List<Expense> returnList = new ArrayList<Expense>();
        for (Expense ex : expenseList) {
            if (ex instanceof HealthcareExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Housing Expenses
    private List<Expense> getHousingExpenses() {
        List<Expense> returnList = new ArrayList<Expense>();
        for (Expense ex : expenseList) {
            if (ex instanceof HousingExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Transportation Expenses
    private List<Expense> getTransportationExpenses() {
        List<Expense> returnList = new ArrayList<Expense>();
        for (Expense ex : expenseList) {
            if (ex instanceof TransportationExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all PersonalExpenses Expenses
    private List<Expense> getPersonalExpenses() {
        List<Expense> returnList = new ArrayList<Expense>();
        for (Expense ex : expenseList) {
            if (ex instanceof PersonalExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

}
