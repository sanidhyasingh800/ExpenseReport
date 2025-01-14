package model.expense;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents an Expense related to food, allows users to add a list of food items purchased in each expense
// Other functionality extended from expense
public class FoodExpense extends Expense {
    private List<String> foodItems;

    public FoodExpense(String name, double amount, String description) {
        super(name, amount, description);
        this.foodItems = new ArrayList<>();
    }

    public FoodExpense(String name, double amount, String description, int year, int month, int day) {
        super(name, amount, description, year, month, day);
        this.foodItems = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds food to the list of foods for this food expense
    public void addFood(String food) {
        foodItems.add(food);
    }

    //EFFECTS: returns the list of food associated with this food expense
    public List<String> getFoodItems() {
        return foodItems;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", "FoodExpense");
        json.put("cost", amount);
        json.put("description", description);
        JSONArray jsonArray = new JSONArray();
        for (String food : foodItems) {
            jsonArray.put(food);
        }
        json.put("foods", foodItems);
        json.put("date", dateOfCreation);
        return json;
    }
}
