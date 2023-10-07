package ui;

import model.ExpenseReport;
import model.Expenses.*;

import java.util.Scanner;

public class ExpenseReportApp {
    private ExpenseReport expenseReport;
    private Scanner user;
    private boolean quit;


    public ExpenseReportApp() {
        runApplication();
        quit = false;
    }

    private void runApplication() {
        setUp();
        String userInput = "";
        while (!quit) {
            showOptions();
            userInput = user.next();
            configureCommand(userInput);
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

    }

    private void showOptions() {
        System.out.println("Please select an Option!");
        System.out.println("Press a to add an Expense");
        System.out.println("Press r to remove an Expense");
        System.out.println("Press v to view all Expense");
        System.out.println("Press c to view Expense by Category");
        System.out.println("Press m to view Expense by cost");
        System.out.println("Press q to quit");
    }

    private void configureCommand(String userInput) {
        switch (userInput) {
            case "a":
                addExpenses();
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
            case "q":
                quit();
                break;
        }
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
        breakBetweenCommands();
    }

    private void displayExpensesBelow(int amount) {
        for (Expense ex : expenseReport.getExpensesBelowAmount(amount)) {
            displayExpense(ex);
        }
    }

    private void displayExpensesAbove(int amount) {
        for (Expense ex : expenseReport.getExpensesAboveAmount(amount)) {
            displayExpense(ex);
        }
    }

    private void viewCategory() {
        int choice = displayCategories();
        for (Expense ex : expenseReport.getSpecificCategoryOfExpense(choice)) {
            displayExpense(ex);
        }
        System.out.println();
        breakBetweenCommands();

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
        for (Expense ex : expenseReport.getExpenses()) {
            displayExpense(ex);
        }
        System.out.println();
        breakBetweenCommands();
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
        breakBetweenCommands();

    }

    // todo: finish after remove Expense is implemented
    private void removeExpense() {
        System.out.println();
        breakBetweenCommands();
    }

    private void breakBetweenCommands() {
        System.out.println("Press enter to continue");
        user.next();
    }

    private void displayExpense(Expense ex) {
        if (ex instanceof FoodExpense) {
            System.out.println("Food Expense - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof HealthcareExpense) {
            System.out.println("Health - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof HousingExpense) {
            System.out.println("Living - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else if (ex instanceof TransportationExpense) {
            System.out.println("Food - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        } else {
            System.out.println("Personal - " + ex.getName() + ": "
                    + ex.getAmount() + "  " + ex.getDescription());
        }
    }
}
