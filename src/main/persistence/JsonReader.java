package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import model.expense.*;
import org.json.*;

// Represents a reader that reads Expense Report from JSON data stored in file
// Code heavily inspired by JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source  file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads expense report from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpenseReport read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExpenseReport(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses expense report from JSON object and returns it
    private ExpenseReport parseExpenseReport(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double budget = jsonObject.getDouble("budget");
        ExpenseReport expenseReport = new ExpenseReport(name, budget);
        addExpenses(expenseReport, jsonObject);
        addSavedExpenses(expenseReport, jsonObject);
        return expenseReport;
    }

    // MODIFIES: expenseReport
    // EFFECTS: parses expenses from JSON object and adds them to expense report
    private void addExpenses(ExpenseReport expenseReport, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject expense = (JSONObject) json;
            addExpense(expenseReport, expense);
        }
    }

    // MODIFIES: expenseReport
    // EFFECTS: parses recurring expenses from JSON object and adds them to
    //          recurring expenses in expense report
    private void addSavedExpenses(ExpenseReport expenseReport, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("savedexpenses");
        for (Object json : jsonArray) {
            JSONObject expense = (JSONObject) json;
            addSavedExpense(expenseReport, expense);
        }
    }

    // MODIFIES: expenseReport
    // EFFECTS: parses expense from JSON object and adds it to expense report
    private void addExpense(ExpenseReport expenseReport, JSONObject jsonObject) {
        expenseReport.setAccessedThroughGraph(true);
        String category = jsonObject.getString("category");
        addExpenseToList(expenseReport.getExpenses(), jsonObject, category);
    }

    // MODIFIES: expenseReport
    // EFFECTS: parses expense from JSON object and adds it to recurring expenses in expense report
    private void addSavedExpense(ExpenseReport expenseReport, JSONObject jsonObject) {
        String category = jsonObject.getString("category");
        addExpenseToList(expenseReport.getEasyAdd(), jsonObject, category);
    }

    // MODIFIES: list
    // EFFECTS: configures the specific category of expense to add to list
    private void addExpenseToList(List<Expense> list, JSONObject jsonObject, String category) {
        switch (category) {
            case "FoodExpense":
                list.add(generateFoodExpense(jsonObject));
                break;
            case "HealthcareExpense":
                list.add(generateHealthcareExpense(jsonObject));
                break;
            case "HousingExpense":
                list.add(generateHousingExpense(jsonObject));
                break;
            case "TransportationExpense":
                list.add(generateTransportationExpense(jsonObject));
                break;
            default:
                list.add(generatePersonalExpense(jsonObject));
                break;
        }
    }

    // EFFECTS: produces a PersonalExpense with the saved data
    private Expense generatePersonalExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("cost");
        String desc = jsonObject.getString("description");
        String dateString = (String) jsonObject.get("date");
        LocalDate date = LocalDate.parse(dateString);
        PersonalExpense ex = new PersonalExpense(name, amount, desc,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        ex.setAsNeed(jsonObject.getBoolean("need"));
        return ex;
    }

    // EFFECTS: produces a TransportationExpense with the saved data
    private Expense generateTransportationExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("cost");
        String desc = jsonObject.getString("description");
        String dateString = (String) jsonObject.get("date");
        LocalDate date = LocalDate.parse(dateString);
        TransportationExpense ex = new TransportationExpense(name, amount, desc,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        ex.setTypeOfTransportation(jsonObject.getInt("type"));
        return ex;
    }

    // EFFECTS: produces a HousingExpense with the saved data
    private Expense generateHousingExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("cost");
        String desc = jsonObject.getString("description");
        String dateString = (String) jsonObject.get("date");
        LocalDate date = LocalDate.parse(dateString);
        HousingExpense ex = new HousingExpense(name, amount, desc,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        ex.setTypeofBill(jsonObject.getInt("bill"));
        return ex;
    }

    // EFFECTS: produces a HealthcareExpense with the saved data
    private Expense generateHealthcareExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("cost");
        String desc = jsonObject.getString("description");
        String dateString = (String) jsonObject.get("date");
        LocalDate date = LocalDate.parse(dateString);
        HealthcareExpense ex = new HealthcareExpense(name, amount, desc,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        ex.coveredByInsurance(jsonObject.getDouble("covered"));
        return ex;
    }

    // EFFECTS: produces a FoodExpense with the saved data
    private Expense generateFoodExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("cost");
        String desc = jsonObject.getString("description");
        String dateString = (String) jsonObject.get("date");
        LocalDate date = LocalDate.parse(dateString);
        FoodExpense ex = new FoodExpense(name, amount, desc,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        JSONArray jsonArray = jsonObject.getJSONArray("foods");
        for (Object json : jsonArray) {
            ex.addFood(json.toString());
        }
        return ex;
    }
}
