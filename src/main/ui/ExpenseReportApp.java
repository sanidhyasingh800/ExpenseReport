package ui;

import model.ExpenseReport;
import model.expense.*;
import model.StatisticsReport;

import java.util.Scanner;
import java.util.List;

public class ExpenseReportApp {
    private ExpenseReport expenseReport;
    private StatisticsReport statisticsReport;
    private Scanner user;
    private boolean quit;


    public ExpenseReportApp() {
        runApplication();
        quit = false;
    }

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


    private void setUp() {
        // Set up scanner
        user = new Scanner(System.in);
        user.useDelimiter("\n");
        System.out.println("Welcome to your very own Expense Assistant!");
        System.out.println("Please select a budget for your expense report:");
        double budget = user.nextDouble();
        expenseReport = new ExpenseReport(budget);
        statisticsReport = new StatisticsReport(expenseReport);

    }

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
        System.out.println("Press q to quit");
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configureCommand(String userInput) {
        switch (userInput) {
            case "a":
                addExpenses();
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
            case "q":
                quit();
                break;
        }
    }

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

    private String provideTimeCommands() {
        System.out.println("Press d to view all expenses today");
        System.out.println("Press w to view all expenses this week");
        System.out.println("Press m to view all expenses this month");
        return user.next();
    }

    private void changeBudget() {
        System.out.println("Enter new Budget");
        double budget = user.nextDouble();
        expenseReport.changeBudget(budget);
        System.out.println("New budget is " + budget);
    }


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
                addNewRecurringExpense();
                break;
            case "r":
                removeRecurringExpense();
                break;
            case "e":
                addRecurringExpenseToReport();
                break;
        }

    }

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

    private void removeRecurringExpense() {
        viewRecurringExpenses();
        System.out.println("Select the index of the expense you no longer need");
        int choice = user.nextInt();
        expenseReport.removeEasyAddExpense(choice - 1);
        System.out.println("Expense Forgotten!");
    }

    private void addNewRecurringExpense() {
        System.out.println("Enter the name of your expense:");
        String name = user.next();
        System.out.println("Enter the category index of your expense:");
        int category = displayCategories();
        System.out.println("Enter the cost of your expense");
        double cost = user.nextDouble();
        System.out.println("Enter the description of your expense: ");
        String description = user.next();
        expenseReport.addEasyExpense(name, cost, description, category);
        System.out.println("Expense item: " + name + " Saved!");

    }

    private void viewRecurringExpenses() {
        List<Expense> list = expenseReport.getEasyAdd();
        displayList(list);
    }

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

        }
    }

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

    private String displayStatisticsOptions() {
        System.out.println("Press t to view Total Spending");
        System.out.println("Press p to view Percentage Statistics");
        System.out.println("Press a to view Average Expense Cost");
        System.out.println("Press s to view Total Number of Expenses and more");
        return user.next();
    }

    private void quit() {
        System.out.println("Thank you for using ExpenseTracker!");
        quit = true;


    }

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

    private void displayExpensesBelow(int amount) {
        List<Expense> list = expenseReport.getExpensesBelowAmount(amount);
        displayList(list);

    }

    private void displayExpensesAbove(int amount) {
        List<Expense> list = expenseReport.getExpensesAboveAmount(amount);
        displayList(list);

    }

    private void viewCategory() {
        int choice = displayCategories();
        List<Expense> list = expenseReport.getSpecificCategoryOfExpense(choice);
        displayList(list);

    }

    private int displayCategories() {
        System.out.println("Press 1 for Food Expenses");
        System.out.println("Press 2 for Health Expenses");
        System.out.println("Press 3 for Living Expenses");
        System.out.println("Press 4 for Transportation Expenses");
        System.out.println("Press 5 for Personal Expenses");
        return user.nextInt();
    }

    private void viewAllExpense() {
        List<Expense> list = expenseReport.getExpenses();
        displayList(list);
    }

    private void addExpenses() {
        System.out.println("Enter the name of your expense:");
        String name = user.next();
        System.out.println("Enter the category index of your expense:");
        int category = displayCategories();
        System.out.println("Enter the cost of your expense");
        double cost = user.nextDouble();
        System.out.println("Enter the description of your expense: ");
        String description = user.next();
        expenseReport.addExpense(name, cost, description, category);
        System.out.println("Expense item: " + name + " added!");

    }

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

    private void displayList(List<Expense> list) {
        if (list.size() == 0) {
            System.out.println("No Expenses Yet!");
            return;
        }
        for (Expense ex: list) {
            displayGeneralExpense(list, ex);
        }
    }

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
