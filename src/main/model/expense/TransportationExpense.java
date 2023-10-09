package model.expense;

// Represents an Expense related to Transportation Costs, allows users to label each expense as a
// green energy, public, or personal form of transportation
// Other functionality extended from expense
public class TransportationExpense extends Expense {
    private int typeOfTransportation;

    public TransportationExpense(String name, double amount, String description) {
        super(name, amount, description);
    }

    public TransportationExpense(String name, double amount, String description, int year, int month, int day) {
        super(name, amount, description, year, month, day);
    }

    //MODIFIES: this
    //EFFECTS: sets the type of transportation referred to by the expense
    //         1 = Green Friendly method of Transportation
    //         2 = public transportation costs
    //         3 = personal transportation (car, motorcycle, private jet, etc).
    public void setTypeOfTransportation(int type) {
        typeOfTransportation = type;

    }

    //EFFECTS: returns type of transportation, see setTypeOfTransportation for representation info
    public int getTypeOfTransportation() {
        return typeOfTransportation;
    }
}
