package model.expense;

// Represents an Expense related to Personal Costs, allows users to label each purchase as a need or want to
// better track spending habits
// Other functionality extended from expense
public class PersonalExpense extends Expense {
    private boolean need;

    public PersonalExpense(String name, double amount, String description) {
        super(name, amount, description);
    }

    public PersonalExpense(String name, double amount, String description, int year, int month, int day) {
        super(name, amount, description, year, month, day);
    }

    //MODIFIES: this
    //EFFECTS: identifies a personal expense as a need (x is true) or a want (x is false)
    public void setAsNeed(boolean x) {
        need = x;
    }

    //EFFECTS: returns true if expense is a need, false if the expense is a want
    public boolean isNeed() {
        return need;
    }
}
