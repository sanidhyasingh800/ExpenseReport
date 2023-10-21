package model.expense;

import org.json.JSONObject;

// Represents an Expense related to Healthcare, allows users deduct insurance covered costs
// Other functionality extended from expense
public class HealthcareExpense extends Expense {
    private double amountCovered = 0;

    public HealthcareExpense(String name, double amount, String description) {
        super(name, amount, description);
    }

    public HealthcareExpense(String name, double amount, String description, int year, int month, int day) {
        super(name, amount, description, year, month, day);
    }

    // REQUIRES: amountCovered >= amount
    // MODIFIES: this
    // EFFECTS: reduces the cost of this healthcare expense if a portion is covered by insurance
    //          and identifies expense as being covered by expense
    public void coveredByInsurance(double amountCovered) {
        this.amountCovered = amountCovered;
        changeExpense(getAmount() - amountCovered);
    }

    public double getAmountCovered() {
        return amountCovered;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", "HealthcareExpense");
        json.put("cost", amount);
        json.put("description", description);
        json.put("covered", amountCovered);
        json.put("date", dateOfCreation);
        return json;
    }
}
