package model;

import model.eventlog.Event;
import model.eventlog.EventLog;
import model.expense.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// Represents an Expense Report that allows you to add and remove Expenses
// and provides functionality to return all expenses, filter by category, cost, and time
public class ExpenseReport implements Writable {
    private String name;
    private final List<Expense> expenseList;
    private final List<Expense> easyAdd;
    private double budget;
    private boolean accessedThroughGraph = false;

    // REQUIRES: budget > 0
    // MODIFIES: this, EventLog
    // EFFECTS: constructs a new Expense Report with the given budget
    //          and no expenses added
    public ExpenseReport(String name, double budget) {
        expenseList = new ArrayList<>();
        easyAdd = new ArrayList<>();
        this.budget = budget;
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Expense Report Created with name: "
                                        + name + " and budget: " + budget));
    }

    // adding expenses:

    // REQUIRES: amount >= 0 and category is one of (1,2,3,4,5)
    // MODIFIES: this, EventLog
    // EFFECTS: adds a new expense with given name, amount, and description to List of Expenses and logs it
    //          category = 1 adds a new Food Expense
    //          category = 2 adds a new Healthcare Expense
    //          category = 3 adds a new Housing Expense
    //          category = 4 adds a new Transportation Expense
    //          category = 5 adds a new Personal Expense
    public void addExpense(String name, double amount, String description, int category) {
        addExpenseGeneral(expenseList, name, amount, description, category);
        logExpenseAdded(name, amount, description, category);
    }

    // MODIFIES: EventLog
    // EFFECTS: logs the expense added with the specific category
    private void logExpenseAdded(String name, double amount, String description, int category) {
        switch (category) {
            case 1:
                logSpecificCategory(name, amount, description, "Food Expense");
                break;
            case 2:
                logSpecificCategory(name, amount, description, "Healthcare Expense");
                break;
            case 3:
                logSpecificCategory(name, amount, description, "Housing Expense");
                break;
            case 4:
                logSpecificCategory(name, amount, description, "Transportation Expense");
                break;
            case 5:
                logSpecificCategory(name, amount, description, "Personal Expense");
                break;
            default:
                break;
        }
    }

    // MODIFIES: EventLog
    // EFFECTS: logs the expense with the given name, cost, description, and category string
    private void logSpecificCategory(String name, double amount, String description, String category) {
        EventLog.getInstance().logEvent(new Event(category +  " created with name: "
                + name + ", cost: " + amount + ", and description: " + description));
    }

    // ONLY FOR TESTING PURPOSES
    // REQUIRES: amount >= 0 and category is one of (1,2,3,4,5)
    // MODIFIES: this
    // EFFECTS: adds a new expense with given name, amount, description and given date to List of Expenses
    //          category = 1 adds a new Food Expense
    //          category = 2 adds a new Healthcare Expense
    //          category = 3 adds a new Housing Expense
    //          category = 4 adds a new Transportation Expense
    //          category = 5 adds a new Personal Expense
    public void addExpenseWithTime(String name, double amount, String description, int category,
                                   int year, int month, int day) {
        addExpenseGeneralWithTime(expenseList, name, amount, description, category, year, month, day);
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
        Expense removed = expenseList.get(i);
        expenseList.remove(i);
        EventLog.getInstance().logEvent(new Event("Deleted Expense with name: "
                + removed.getName() + " and cost: " + removed.getAmount()));
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
    //         in the order they were added
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
    //         in the order they were added
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
    //         in the order they were added
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
    //          returns the expenses in the order they were added
    public List<Expense> filterByDay() {
        List<Expense> returnList = new ArrayList<>();
        for (Expense t : expenseList) {
            if (t.getDateOfCreation().equals(LocalDate.now())) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    // EFFECTS: returns the expenses made in the last 30 days, empty list if none
    //          returns the expenses in the order they were added
    public List<Expense> filterByMonth() {
        List<Expense> returnList = new ArrayList<>();
        LocalDate l = LocalDate.now().minusDays(30);
        for (Expense t : expenseList) {
            if (t.getDateOfCreation().isAfter(l)) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    // EFFECTS: returns the expenses made in the last 7 days, empty list if none
    //          returns the expenses in the order they were added
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

    //REQUIRES: expenseList is NOT empty
    //EFFECTS: returns the most recently added expense
    public Expense getMostRecent() {
        return expenseList.get(expenseList.size() - 1);
    }

    //REQUIRES: easyAdd is NOT empty
    //EFFECTS: returns the most recently added saved expenses
    public Expense getMostRecentEasyAddExpense() {
        return easyAdd.get(easyAdd.size() - 1);
    }



    // budget methods:

    //MODIFIES: this
    //EFFECTS: changes the budget of the expense report to newBudget
    public void changeBudget(double newBudget) {
        this.budget = newBudget;
    }

    // Getters:

    public List<Expense> getExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Overall Expense Report Viewed"));
        }
        return expenseList;
    }

    public List<Expense> getEasyAdd() {
        return easyAdd;
    }

    public double getBudget() {
        return budget;
    }

    public String getName() {
        return name;
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
            default:
                break;
        }
    }

    // ONLY FOR TESTING PURPOSES
    //MODIFIES: this
    //EFFECTS: adds expense with given name, amount, description, category index (see addExpense),
    //         and date of year, month, day
    //         to the specified list
    private void addExpenseGeneralWithTime(List<Expense> list, String name, double amount, String description,
                                           int category, int year, int month, int day) {
        switch (category) {
            case 1:
                list.add(new FoodExpense(name, amount, description, year, month, day));
                break;
            case 2:
                list.add(new HealthcareExpense(name, amount, description,year, month, day));
                break;
            case 3:
                list.add(new HousingExpense(name, amount, description, year, month, day));
                break;
            case 4:
                list.add(new TransportationExpense(name, amount, description, year, month, day));
                break;
            case 5:
                list.add(new PersonalExpense(name, amount, description, year, month, day));
                break;
            default:
                break;
        }
    }

    //EFFECTS: returns all Food Expenses
    //         in the order they were added and logs it
    private List<Expense> getFoodExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Food Expense Report Viewed"));
        }
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof FoodExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Healthcare Expenses
    //         in the order they were added and logs it
    private List<Expense> getHealthExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Health Expense Report Viewed"));
        }
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof HealthcareExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Housing Expenses
    //         in the order they were added and logs it
    private List<Expense> getHousingExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Housing Expense Report Viewed"));
        }
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof HousingExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all Transportation Expenses
    //         in the order they were added and logs it
    private List<Expense> getTransportationExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Transportation Expense Report Viewed"));
        }
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof TransportationExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    //EFFECTS: returns all PersonalExpenses Expenses
    //         in the order they were added and logs it
    private List<Expense> getPersonalExpenses() {
        if (!accessedThroughGraph) {
            EventLog.getInstance().logEvent(new Event("Personal Expense Report Viewed"));
        }
        List<Expense> returnList = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex instanceof PersonalExpense) {
                returnList.add(ex);
            }
        }
        return returnList;
    }

    // Data Persistence

    // MODIFIES: EventLog
    // EFFECTS: returns expense report as a JSON object and logs it
    @Override
    public JSONObject toJson() {
        EventLog.getInstance().logEvent(new Event("Expense Report: "
                + name + " saved to file"));
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("budget", budget);
        json.put("expenses", expensesToJson());
        json.put("savedexpenses", savedExpensesToJson());
        return json;
    }

    // EFFECTS: returns expenses in report as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense ex : expenseList) {
            jsonArray.put(ex.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns recurring expenses in report as a JSON array
    private JSONArray savedExpensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense ex : easyAdd) {
            jsonArray.put(ex.toJson());
        }

        return jsonArray;
    }

    public void setAccessedThroughGraph(Boolean b) {
        accessedThroughGraph = b;
    }


}
