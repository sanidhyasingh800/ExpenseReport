package model.expense;

// Represents an Expense related to Living Costs, allows users to identify each living expense with a type of bill
// User can choose from water, electricity, Trash, Internet, rent, and mortgage
// Other functionality extended from expense
public class HousingExpense extends Expense {
    private int typeofBill;

    public HousingExpense(String name, double amount, String description) {
        super(name, amount, description);
    }

    public HousingExpense(String name, double amount, String description, int year, int month, int day) {
        super(name, amount, description, year, month, day);
    }

    // MODIFIES: this
    // EFFECTS: sets the type of bill associated with housing expenses to "type"
    //          1 = Water Bill
    //          2 = Electricity Bill
    //          3 = Trash / Recycling Bill
    //          4 = Internet Bill
    //          5 = Rent / Mortgage
    public void setTypeofBill(int type) {
        typeofBill = type;
    }

    // EFFECTS: returns the type of bill associated with housing expenses
    //          (see setTypeofBill for representation information)
    public int getTypeofBill() {
        return typeofBill;
    }
}
