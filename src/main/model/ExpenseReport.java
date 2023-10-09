package model;

import model.expense.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// Represents an Expense Report that allows you to add and remove Expenses
// and provides functionality to return all expenses, filter by category, cost, and time
public class ExpenseReport {
    private final List<Expense> expenseList;
    private final List<Expense> easyAdd;
    private double budget;

    // REQUIRES: budget > 0
    // EFFECTS: constructs a new Expense Report with the given budget
    //          and no expenses added
    public ExpenseReport(double budget) {
        expenseList = new ArrayList<>();
        easyAdd = new ArrayList<>();
        this.budget = budget;
    }

    // adding expenses:

    // REQUIRES: amount >= 0 and category is one of (1,2,3,4,5)
    // MODIFIES: this
    // EFFECTS: adds a new expense with given name, amount, and description to List of Expenses
    //          category = 1 adds a new Food Expense
    //          category = 2 adds a new Healthcare Expense
    //          category = 3 adds a new Housing Expense
    //          category = 4 adds a new Transportation Expense
    //          category = 5 adds a new Personal Expense
    public void addExpense(String name, double amount, String description, int category) {
        addExpenseGeneral(expenseList, name, amount, description, category);
    }

    // MODIFIES: this
    // EFFECTS: adds the expense at index from saved expenses to expense report
    public void addEasyExpenseToReport(int index) {
        expenseList.add(easyAdd.get(index));
    }

    // REQUIRES: amount >= 0 and category is one of (1,2,3,4,5)
    // MODIFIES: this
    // EFFECTS: adds a new expense to the saved list of easy add expenses
    //          (see addExpense for category indexing)
    public void addEasyExpense(String name, double amount, String description, int category) {
        addExpenseGeneral(easyAdd, name, amount, description, category);
    }


    //MODIFIES: this
    //EFFECTS: removes expense at index i from List of Expenses
    public void removeExpense(int i) {
        expenseList.remove(i);
    }

    // Removing expenses

    // MODIFIES: this
    // EFFECTS: removes all expenses with the given name
    public void removeExpenses(String name) {
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getName().equals(name)) {
                expenseList.remove(i);
                i--;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: removes expense at index i from List of Expenses
    public void removeEasyAddExpense(int i) {
        easyAdd.remove(i);
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

    // filtering by time:

    // EFFECTS: returns the expenses made in the last day, empty list if none
    public List<Expense> filterByDay() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense t : expenseList) {
            if (t.getDateOfCreation().equals(LocalDate.now())) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    // EFFECTS: returns the expenses made in the last month, empty list if none
    public List<Expense> filterByMonth() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense t : expenseList) {
            if (t.getDateOfCreation().getMonth().equals(LocalDate.now().getMonth())) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    // EFFECTS: returns the expenses made in the last 7 days, empty list if none
    public List<Expense> filterByWeek() {
        List<Expense> returnList = new ArrayList<>();
        LocalDate l = LocalDate.now().minusDays(7);
        for (Expense t : expenseList) {
            if (t.getDateOfCreation().isAfter(l)) {
                returnList.add(t);
            }
        }
        return returnList;
    }



    // budget methods:

    //MODIFIES: this
    //EFFECTS: changes the budget of the expense report to newBudget
    public void changeBudget(double newBudget) {
        this.budget = newBudget;
    }

    // Getters:

    public List<Expense> getExpenses() {
        return expenseList;
    }

    public List<Expense> getEasyAdd() {
        return easyAdd;
    }

    public double getBudget() {
        return budget;
    }

    public Expense getMostRecent() {
        return expenseList.get(expenseList.size() - 1);
    }

    public Expense getMostRecentEasyAddExpense() {
        return easyAdd.get(easyAdd.size() - 1);
    }


    // Private Helper Methods

    //MODIFIES: this
    //EFFECTS: adds expense with given name, amount, description, and category index (see addExpense)
    //         to the specified list
    private void addExpenseGeneral(List<Expense> list, String name, double amount, String description, int category) {
        switch (category) {
            case 1:
                list.add(new FoodExpense(name, amount, description));
                break;
            case 2:
                list.add(new HealthcareExpense(name, amount, description));
                break;
            case 3:
                list.add(new HousingExpense(name, amount, description));
                break;
            case 4:
                list.add(new TransportationExpense(name, amount, description));
                break;
            case 5:
                list.add(new PersonalExpense(name, amount, description));
                break;
        }
    }

    //EFFECTS: returns all Food Expenses
    private List<Expense> getFoodExpenses() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof FoodExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Healthcare Expenses
    private List<Expense> getHealthExpenses() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof HealthcareExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Housing Expenses
    private List<Expense> getHousingExpenses() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof HousingExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Transportation Expenses
    private List<Expense> getTransportationExpenses() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof TransportationExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all PersonalExpenses Expenses
    private List<Expense> getPersonalExpenses() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof PersonalExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

}
