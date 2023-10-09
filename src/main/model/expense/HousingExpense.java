package model.expense;

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
