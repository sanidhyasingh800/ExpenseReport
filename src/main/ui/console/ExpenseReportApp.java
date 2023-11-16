package ui.console;

import model.ExpenseReport;
import model.expense.*;
import model.StatisticsReport;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

// Displays and Runs the Expense Report App. Provides options for users to add, save, and delete expenses
// Users can view expenses filter by parameters such as category, cost, and time as well as view
// statistics based on their reports. Users can also set a budget for their report and view customized
// statistics for each time of expense (food, housing, health, transportation, personal)
public class ExpenseReportApp {
    private static final String JSON_STORE = "./data/expense.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;
    private Scanner user;
    private boolean quit;

    // EFFECTS: Runs the Expense Report App
    public ExpenseReportApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApplication();
        quit = false;
    }

    // EFFECTS: saves the expense report to file
    private void saveReport() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenseReport);
            jsonWriter.close();
            System.out.println("Saved " + expenseReport.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads expense report from file
    private void loadReport() {
        try {
            expenseReport = jsonReader.read();
            statisticsReport = new StatisticsReport(expenseReport);
            System.out.println("Loaded " + expenseReport.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the start-up menu, continues execution until user chooses to quit
    private void runApplication() {
        setUp();
        String userInput;
        while (!quit) {
            showOptions();
            userInput = user.next();
            configureCommand(userInput);
            if (!quit) {
                breakBetweenCommands();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the scanner object to read after each newline character,
    //          sets the budget for the Expense Report
    private void setUp() {
        // Set up scanner
        user = new Scanner(System.in);
        user.useDelimiter("\n");
        System.out.println("Welcome to your very own Expense Assistant!");
        System.out.println("Press l to load in your saved report, n to skip");
        String choice = user.next();
        if (choice.equals("l")) {
            try {
                loadReport();
            } catch (JSONException e) {
                System.out.println("No saved data yet!");
                initialSetup();
            }
        } else {
            initialSetup();
        }

    }

    // MODIFIES: this
    // EFFECTS: constructs the expense report with given name and budget
    private void initialSetup() {
        System.out.println("Please enter a name for your new report");
        String name = user.next();
        System.out.println("Please select a budget for your new expense report:");
        double budget = user.nextDouble();
        expenseReport = new ExpenseReport(name, budget);
        statisticsReport = new StatisticsReport(expenseReport);
    }


    // EFFECTS: displays the options for users to proceed
    private void showOptions() {
        System.out.println("Please select an Option!");
        System.out.println("Press a to add an Expense");
        System.out.println("Press x to access recurring Expenses");
        System.out.println("Press r to remove an Expense");
        System.out.println("Press v to view all Expense");
        System.out.println("Press c to view Expense by Category");
        System.out.println("Press m to view Expense by cost");
        System.out.println("Press t to break down the expense report by time");
        System.out.println("Press s to view Statistics");
        System.out.println("Press b to change budget");
        System.out.println("Press k to save report");
        System.out.println("Press q to quit");
    }

    //MODIFIES: this
    // EFFECTS: configures the user's command the performs the given action
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configureCommand(String userInput) {
        switch (userInput) {
            case "a":
                addExpenses(1);
                break;
            case "x":
                recurringExpensesOptions();
                break;
            case "r":
                removeExpense();
                break;
            case "v":
                viewAllExpense();
                break;
            case "c":
                viewCategory();
                break;
            case "m":
                viewCostFilteredExpenses();
                break;
            case "t":
                breakDownByTime();
                break;
            case "s":
                viewStatistics();
                break;
            case "b":
                changeBudget();
                break;
            case "k":
                saveReport();
                break;
            case "q":
                quit();
                break;
        }
    }

    // EFFECTS: configures the command provided by the user regarding Time Filtering
    private void breakDownByTime() {
        String choice = provideTimeCommands();
        switch (choice) {
            case "d":
                viewExpensesToday();
                break;
            case "w":
                viewExpensesWeekly();
                break;
            case "m":
                viewExpensesMonthly();
                break;
        }
    }


    // EFFECTS: prints the expenses made this month in format specified by displayGeneralExpense
    private void viewExpensesMonthly() {
        List<Expense> list = expenseReport.filterByMonth();
        displayList(list);
        displayTimeStatistics(3);

    }

    // EFFECTS: prints the expenses made this week in format specified by displayGeneralExpense
    private void viewExpensesWeekly() {
        List<Expense> list = expenseReport.filterByWeek();
        displayList(list);
        displayTimeStatistics(2);

    }

    // EFFECTS: prints the expenses made today in format specified by displayGeneralExpense
    private void viewExpensesToday() {
        List<Expense> list = expenseReport.filterByDay();
        displayList(list);
        displayTimeStatistics(1);

    }

    // EFFECTS: prints the options available to filter by timeframe, returns the input from user
    private String provideTimeCommands() {
        System.out.println("Press d to view all expenses today");
        System.out.println("Press w to view all expenses this week");
        System.out.println("Press m to view all expenses this month");
        return user.next();
    }

    //MODIFIES: this
    //EFFECTS: changes the budget of the report to the amount provided by user
    private void changeBudget() {
        System.out.println("Enter new Budget");
        double budget = user.nextDouble();
        expenseReport.changeBudget(budget);
        System.out.println("New budget is " + budget);
    }

    // MODIFIES : this
    // EFFECTS: provides options to user regarding saved expenses and performs given command
    private void recurringExpensesOptions() {
        System.out.println("Press v to view all recurring expenses");
        System.out.println("Press a to set up a new recurring expense");
        System.out.println("Press r to disable a recurring expense");
        System.out.println("Press e to add a recurring expense to expense report");
        String choice = user.next();
        switch (choice) {
            case "v":
                viewRecurringExpenses();
                break;
            case "a":
                addExpenses(2);
                break;
            case "r":
                removeRecurringExpense();
                break;
            case "e":
                addRecurringExpenseToReport();
                break;
        }

    }

    //MODIFIES: this
    //EFFECTS: adds the saved expense identified by user to the overall expense report
    private void addRecurringExpenseToReport() {
        viewRecurringExpenses();
        if (expenseReport.getEasyAdd().size() == 0) {
            return;
        }
        System.out.println("Select the index of the expense you want to add");
        int choice = user.nextInt();
        expenseReport.addEasyExpenseToReport(choice - 1);
        System.out.println("Expense Added!");
    }

    // MODIFIES: this
    // EFFECTS: deletes the saved expense stored at the index given by the user
    private void removeRecurringExpense() {
        viewRecurringExpenses();
        System.out.println("Select the index of the expense you no longer need");
        int choice = user.nextInt();
        expenseReport.removeEasyAddExpense(choice - 1);
        System.out.println("Expense Forgotten!");
    }

    // EFFECTS: prints all the saved expenses
    private void viewRecurringExpenses() {
        List<Expense> list = expenseReport.getEasyAdd();
        displayList(list);
    }

    // EFFECTS: provides users with the options to view different statistics and performs command
    private void viewStatistics() {
        String choice = displayStatisticsOptions();
        switch (choice) {
            case "t" :
                viewTotalSpending();
                break;
            case "p" :
                viewPercentages();
                break;
            case "a" :
                viewAverages();
                break;
            case "s" :
                viewTotalExpenses();
                break;
            case "c":
                viewStatisticsByCategory();
                break;

        }
    }

    // EFFECTS: provides users with the choice to view the statistics of different categories
    private void viewStatisticsByCategory() {
        int choice = displayCategories();
        switch (choice) {
            case 1:
                statisticsForFood();
                break;
            case 2:
                statisticsForHealth();
                break;
            case 3:
                statisticsForHousing();
                break;
            case 4:
                statisticsForTransportation();
                break;
            case 5:
                statisticsForPersonal();
                break;
        }
    }

    // EFFECTS: prints the statistics related to food expenses
    //          such as average cost per food item
    private void statisticsForFood() {
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(1);
        System.out.println("Average Cost Per Food Item: " + statisticsReport.getAverageAmountPerFoodItem());
        displayList(list);
        System.out.println("Type in the index of any expense to view the foods bought");
        System.out.println("Type -1 to skip");
        int choice = user.nextInt();
        if (choice != -1) {
            Expense ex = list.get(choice - 1);
            for (String food : ((FoodExpense) ex).getFoodItems()) {
                System.out.println(food);
            }
        }
    }

    // EFFECTS: prints the statistics related to health expenses
    //          such as insurance savings
    private void statisticsForHealth() {
        System.out.println("Insurance has saved you " + statisticsReport.totalAmountSaved());
        System.out.println("Insurance has covered " + statisticsReport.percentageCoveredByInsurance()
                            + " of all Healthcare Expenses");

    }

    // EFFECTS: prints the statistics related to housing costs
    //          such as cost of each bill
    private void statisticsForHousing() {
        List<Double> list = statisticsReport.getBillsSummary();
        System.out.println("Amount spent on Water Bills " + list.get(0));
        System.out.println("Amount spent on Electricity Bills " + list.get(1));
        System.out.println("Amount spent on Trash/Recycling " + list.get(2));
        System.out.println("Amount spent on Internet " + list.get(3));
        System.out.println("Amount spent on Rent/Mortgage " + list.get(4));

    }

    // EFFECTS: prints the statistics related to transportation costs
    //          such as cost associated with each method of transportation
    private void statisticsForTransportation() {
        System.out.println("Amount spent on Eco-Friendly travel: "
                            + statisticsReport.totalSpentOnGreenEnergy());
        System.out.println("Amount spent on Public Transportation: "
                + statisticsReport.totalSpentOnPublicTransportation());
        System.out.println("Amount spent on Private Transportation: "
                + statisticsReport.totalSpentOnPersonalTransportation());

    }

    // EFFECTS: prints the statistics related to personal costs
    //          such as cost needs and wants
    private void statisticsForPersonal() {
        System.out.println("Amount on money spent on Needs: "
                + statisticsReport.totalAmountOnNeeds());
        System.out.println("Amount on money spent on Wants: "
                + statisticsReport.totalAmountOnWants());

    }


    // EFFECTS: prints the average cost of an expense overall and in each category
    private void viewAverages() {
        double total = statisticsReport.averageCost();
        System.out.println("Your Average Expense costs " + total);
        total = statisticsReport.averageCostByCategory(1);
        System.out.println("You spend " + total + " on Food on average");
        total = statisticsReport.averageCostByCategory(2);
        System.out.println("You spend " + total + " on Healthcare on average");
        total = statisticsReport.averageCostByCategory(3);
        System.out.println("You spend " + total + " on Living Costs on average");
        total = statisticsReport.averageCostByCategory(4);
        System.out.println("You spend " + total + " on Transportation on average");
        total = statisticsReport.averageCostByCategory(5);
        System.out.println("You spend " + total + " on Personal Expenses on average");
        System.out.println();
    }

    // EFFECTS: prints the percentage of total money spent in each category
    private void viewPercentages() {
        double total = statisticsReport.percentageOfCategory(1);
        System.out.println("You Spent " + total + "% on Food");
        total = statisticsReport.percentageOfCategory(2);
        System.out.println("You Spent " + total + "% on Healthcare");
        total = statisticsReport.percentageOfCategory(3);
        System.out.println("You Spent " + total + "% on Living Costs");
        total = statisticsReport.percentageOfCategory(4);
        System.out.println("You Spent " + total + "% on Transportation");
        total = statisticsReport.percentageOfCategory(5);
        System.out.println("You Spent " + total + "% on Personal Expenses");
    }

    // EFFECTS: prints the total amount spent overall and for each category
    private void viewTotalSpending() {
        double total = statisticsReport.totalSpending();
        System.out.println("Your Total Spending is " + total + " with "
                + statisticsReport.budgetLeft() + " left");
        total = statisticsReport.totalSpendingByCategory(1);
        System.out.println("You Spent " + total + " on Food");
        total = statisticsReport.totalSpendingByCategory(2);
        System.out.println("You Spent " + total + " on Healthcare");
        total = statisticsReport.totalSpendingByCategory(3);
        System.out.println("You Spent " + total + " on Living Costs");
        total = statisticsReport.totalSpendingByCategory(4);
        System.out.println("You Spent " + total + " on Transportation");
        total = statisticsReport.totalSpendingByCategory(5);
        System.out.println("You Spent " + total + " on Personal Expenses");
    }

    // EFFECTS: prints the total number of expenses overall and in each category
    private void viewTotalExpenses() {
        int total = statisticsReport.totalExpenses();
        System.out.println("Your Total Number of Expenses is " + total);
        total = statisticsReport.totalExpensesInCategory(1);
        System.out.println("You have " + total + " expenses on Food");
        total = statisticsReport.totalExpensesInCategory(2);
        System.out.println("You have " + total + " expenses on Healthcare");
        total = statisticsReport.totalExpensesInCategory(3);
        System.out.println("You have " + total + " expenses on Living Costs");
        total = statisticsReport.totalExpensesInCategory(4);
        System.out.println("You have " + total + " expenses on Transportation");
        total = statisticsReport.totalExpensesInCategory(5);
        System.out.println("You have " + total + " expenses on Personal Expenses");
    }

    //EFFECTS: displays the statistics processing options available and returns the user input
    private String displayStatisticsOptions() {
        System.out.println("Press t to view Total Spending");
        System.out.println("Press p to view Percentage Statistics");
        System.out.println("Press a to view Average Expense Cost");
        System.out.println("Press s to view Total Number of Expenses and more");
        System.out.println("Press c to view statistics by categories");
        return user.next();
    }

    // EFFECTS: terminates the app.
    private void quit() {
        System.out.println("Thank you for using ExpenseTracker!");
        quit = true;


    }

    // EFFECTS: prompts user to filter above or below cost and displays the respective list of expenses
    private void viewCostFilteredExpenses() {
        System.out.println("Enter amount you would like to filter by: ");
        int amount = user.nextInt();
        System.out.println("Press u to view expenses that cost more than " + amount);
        System.out.println("Press l to view expenses that cost less than " + amount);
        String choice = user.next();
        if (choice.equals("u")) {
            displayExpensesAbove(amount);

        } else {
            displayExpensesBelow(amount);
        }
    }

    //EFFECTS: prints all expenses above or equal to amount
    private void displayExpensesBelow(int amount) {
        List<Expense> list = expenseReport.getExpensesBelowAmount(amount);
        displayList(list);

    }

    //EFFECTS: prints all expenses below or equal to amount
    private void displayExpensesAbove(int amount) {
        List<Expense> list = expenseReport.getExpensesAboveAmount(amount);
        displayList(list);

    }

    //EFFECTS: prints all expenses within a specified category
    private void viewCategory() {
        int choice = displayCategories();
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(choice);
        displayList(list);

    }

    // EFFECTS: provides the category index and returns the user input
    private int displayCategories() {
        System.out.println("Press 1 for Food Expenses");
        System.out.println("Press 2 for Health Expenses");
        System.out.println("Press 3 for Living Expenses");
        System.out.println("Press 4 for Transportation Expenses");
        System.out.println("Press 5 for Personal Expenses");
        return user.nextInt();
    }

    // EFFECTS: prints all expenses in a list
    private void viewAllExpense() {
        List<Expense> list = expenseReport.getExpenses();
        displayList(list);
    }

    // MODIFIES: this
    // EFFECTS: adds the user-inputted expense to the expense report
    private void addExpenses(int whichList) {
        System.out.println("Enter the category index of your expense:");
        int category = displayCategories();
        switch (category) {
            case 1:
                setUpFoodExpense(whichList);
                break;
            case 2:
                setUpHealthCareExpense(whichList);
                break;
            case 3:
                setUpHousingExpense(whichList);
                break;
            case 4:
                setUpTransportationExpense(whichList);
                break;
            case 5:
                setUpPersonalExpense(whichList);
                break;
        }

    }

    // MODIFIES: this
    // EFFECTS: configures a food expense as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private void setUpFoodExpense(int whichList) {
        Expense ex = expenseBasicSetUp(1, whichList);
        System.out.println("Press y to enter a list of food items to this expense, n to skip");
        String choice = user.next();
        if (choice.equals("y")) {
            setUpFoodList(ex);
        }
        System.out.println("Expense item: " + ex.getName() + " added!");

    }

    // MODIFIES: this
    // EFFECTS: adds a list of foods to the food expense being added
    private void setUpFoodList(Expense ex) {
        boolean cont = true;
        while (cont) {
            System.out.println("Type the name of food and press enter: ");
            String food = user.next();
            ((FoodExpense) ex).addFood(food);
            System.out.println(food + " added! Press Enter to add more food, or n to stop");
            String choice = user.next();
            if (choice.equals("n")) {
                cont = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: configures a Health expense as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private void setUpHealthCareExpense(int whichList) {
        Expense ex = expenseBasicSetUp(2, whichList);
        System.out.println("Press i to add any amount covered by Insurance, n to skip");
        String choice = user.next();
        if (choice.equals("i")) {
            System.out.println("Enter the amount covered by Insurance");
            int amount = user.nextInt();
            ((HealthcareExpense) ex).coveredByInsurance(amount);
            System.out.println("You spent " + ex.getAmount() + " out of pocket");
        }
        System.out.println("Expense item: " + ex.getName() + " added!");
    }

    // MODIFIES: this
    // EFFECTS: configures a Housing expense as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private void setUpHousingExpense(int whichList) {
        Expense ex = expenseBasicSetUp(3, whichList);
        System.out.println("Press y to label expense as a bill, n to skip");
        String choice = user.next();
        if (choice.equals("y")) {
            int type = displayBillIndices();
            ((HousingExpense) ex).setTypeofBill(type);
        }

        System.out.println("Expense item: " + ex.getName() + " added!");
    }

    //EFFECTS: displays the Bill Index and returns user input
    private int displayBillIndices() {
        System.out.println("Enter 1 to add a Water Bill");
        System.out.println("Enter 2 to add an Electricity Bill");
        System.out.println("Enter 3 to add a Trash/Recycling Bill");
        System.out.println("Enter 4 to add an Internet Bill");
        System.out.println("Enter 5 to add a Rent/Mortgage Payment");
        return user.nextInt();
    }

    // MODIFIES: this
    // EFFECTS: configures a transportation expense as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private void setUpTransportationExpense(int whichList) {
        Expense ex = expenseBasicSetUp(4, whichList);
        System.out.println("Press y to label transportation type, n to skip");
        String choice = user.next();
        if (choice.equals("y")) {
            System.out.println("Enter 1 for Eco-friendly Travel");
            System.out.println("Enter 2 for Public Transportation");
            System.out.println("Enter 3 for Private Transportation");
            int type = user.nextInt();
            ((TransportationExpense) ex).setTypeOfTransportation(type);
        }
        System.out.println("Expense item: " + ex.getName() + " added!");
    }

    // MODIFIES: this
    // EFFECTS: configures a Personal expense as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private void setUpPersonalExpense(int whichList) {
        Expense ex = expenseBasicSetUp(5, whichList);
        System.out.println("Press n to classify expense as a need");
        System.out.println("Press w to classify expense as a want");
        String choice = user.next();
        if (choice.equals("n")) {
            ((PersonalExpense) ex).setAsNeed(true);
        } else if (choice.equals("w")) {
            ((PersonalExpense) ex).setAsNeed(false);
        }
        System.out.println("Expense item: " + ex.getName() + " added!");
    }

    // MODIFIES: this
    // EFFECTS: configures a basic Expense (name, cost, description) as per user input and adds it to:
    //          whichList = 1 (Expense Report)
    //          whichList = 2 (Saved Expenses)
    private Expense expenseBasicSetUp(int category, int whichList) {
        System.out.println("Enter the name of your expense:");
        String name = user.next();
        System.out.println("Enter the cost of your expense");
        double cost = user.nextDouble();
        System.out.println("Enter the description of your expense: ");
        String description = user.next();
        if (whichList == 1) {
            expenseReport.addExpense(name, cost, description, category);
            return expenseReport.getMostRecent();
        } else {
            expenseReport.addEasyExpense(name, cost, description, category);
            return expenseReport.getMostRecentEasyAddExpense();
        }
    }

    // MODIFIES: this
    // EFFECTS: provides users with different ways to remove expenses and performs the given method of removing
    private void removeExpense() {
        viewAllExpense();
        if (expenseReport.getExpenses().size() == 0) {
            return;
        }
        System.out.println();
        System.out.println("Press i to delete a specific expense");
        System.out.println("Press n to delete all expenses by name");
        String choice = user.next();
        if (choice.equals("i")) {
            System.out.println("Which expense would you like to delete (specify index)");
            int index = user.nextInt();
            expenseReport.removeExpense(index - 1);
        } else if (choice.equals("n")) {
            System.out.println("Enter the name of the expenses you would like to delete");
            String name = user.next();
            expenseReport.removeExpenses(name);
        }
        System.out.println("Expense Deleted!");
        viewAllExpense();
    }

    // EFFECTS: provides a break between commands and waits for user to press enter
    private void breakBetweenCommands() {
        System.out.println("Press enter to continue");
        user.next();
    }

    //EFFECTS: Prints the statistics, total spending, number of expenses, and average cost
    //         for each time frame
    //         time = 1 prints daily statistics
    //         time = 2 prints weekly statistics
    //         time = 3 prints monthly statistics
    private void displayTimeStatistics(int time) {
        switch (time) {
            case 1:
                System.out.println("You spent " + statisticsReport.totalSpendingToday()
                                    + " today on " + statisticsReport.totalExpensesToday()
                                    + " expenses, with an average of " + statisticsReport.averageSpendingToday()
                                    + " on each expense.");
                break;
            case 2:
                System.out.println("You spent " + statisticsReport.totalSpendingWeekly()
                        + " this week on " + statisticsReport.totalExpensesWeekly()
                        + " expenses, with an average of " + statisticsReport.averageSpendingWeekly()
                        + " on each expense.");
                break;
            case 3:
                System.out.println("You spent " + statisticsReport.totalSpendingMonthly()
                        + " this month on " + statisticsReport.totalExpensesMonthly()
                        + " expenses with an average of " + statisticsReport.averageSpendingMonthly()
                        + " on each expense.");
                break;
        }
    }

    // EFFECTS: provides a fixed method to print all lists of expenses,
    //          prints "No Expenses Yet" if list is empty
    private void displayList(List<Expense> list) {
        if (list.size() == 0) {
            System.out.println("No Expenses Yet!");
            return;
        }
        for (Expense ex: list) {
            displayGeneralExpense(list, ex);
        }
    }

    // EFFECTS: prints an expense in the format
    //          index. Category-Name: cost  Description
    private void displayGeneralExpense(List<Expense> list, Expense ex) {
        int index = list.indexOf(ex) + 1;
        if (ex instanceof FoodExpense) {
            System.out.println(index + ". Food Expense - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof HealthcareExpense) {
            System.out.println(index + ". Health - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof HousingExpense) {
            System.out.println(index + ". Living - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof TransportationExpense) {
            System.out.println(index + ". Transportation - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else {
            System.out.println(index + ". Personal - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        }
    }
}
