package model.expense;

import java.time.LocalDate;

//General Model of an expense with specified name, category, amount, description
//Each expense is assigned the date it is created on
public abstract class Expense {
    private String name;
    private double amount;
    private String description;
    private final LocalDate dateOfCreation;

    //EFFECTS: Constructs a general expense with given name, amount, and description
    public Expense(String name, double amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        dateOfCreation = LocalDate.now();
    }

    // ONLY FOR TESTING PURPOSES
    //REQUIRES: year, month, day MUST form a valid date not in the future
    //EFFECTS: Constructs a general expense with given name, amount, and description
    public Expense(String name, double amount, String description, int year, int month, int day) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        dateOfCreation = LocalDate.of(year, month, day);
    }

    //REQUIRES: newAmount >= 0.00
    //MODIFIERS: this
    //EFFECTS: changes the cost of the expense to newAmount
    public void changeExpense(double newAmount) {
        amount = newAmount;

    }

    // Getters:

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }



}
