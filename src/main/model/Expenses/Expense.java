package model.Expenses;

//General Model of an expense with specified name, category, amount, description
public abstract class Expense {
    private String name;
    private double amount;
    private String description;

    //EFFECTS: Constructs a general expense with given name, amount, and description
    public Expense(String name, double amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the expense to newName
    public void changeName(String newName) {
        name = newName;
    }

    //REQUIRES: newAmount >= 0.00
    //MODIFIERS: this
    //EFFECTS: changes the cost of the expense to newAmount
    public void changeExpense(double newAmount) {
        amount = newAmount;

    }

    // MODIFIES: this
    // EFFECTS: changes the name of the expense to newDescription
    public void changeDescription(String newDescription) {
        description = newDescription;
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



}
