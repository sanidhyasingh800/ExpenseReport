package persistence;


import model.ExpenseReport;
import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code heavily inspired by JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest {
    private ExpenseReport expenseReport;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private Expense expense5;


    @BeforeEach
    void setup() {
        expenseReport = new ExpenseReport("Sunny", 100);
        initializeExpenses();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterReportNoExpenses() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterReportNoExpenses.json");
            writer.open();
            writer.write(expenseReport);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterReportNoExpenses.json");
            expenseReport = reader.read();
            assertEquals("Sunny", expenseReport.getName());
            assertEquals(100, expenseReport.getBudget());
            assertEquals(0, expenseReport.getExpenses().size());
            assertEquals(0, expenseReport.getEasyAdd().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralExpenses() {
        try {
            addAllExpenses();
            FoodExpense testFoodExpense = (FoodExpense) expenseReport.getExpenses().get(0);
            testFoodExpense.addFood("Milk");
            testFoodExpense.addFood("Bread");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralExpenses.json");
            writer.open();
            writer.write(expenseReport);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralExpenses.json");
            expenseReport = reader.read();
            assertEquals("Sunny", expenseReport.getName());
            List<Expense> testList = expenseReport.getExpenses();
            assertEquals(3, testList.size());
            checkForCorrectExpense(expense1, testList.get(0));
            List<String> foods = ((FoodExpense) testList.get(0)).getFoodItems();
            assertEquals(2, foods.size());
            assertEquals("Milk", foods.get(0));
            assertEquals("Bread", foods.get(1));
            checkForCorrectExpense(expense2, testList.get(1));
            checkForCorrectExpense(expense3, testList.get(2));
            testList = expenseReport.getEasyAdd();
            checkForCorrectExpense(expense4, testList.get(0));
            checkForCorrectExpense(expense5, testList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    private void checkForCorrectExpense(Expense expected, Expense returned) {
        assertEquals(expected.getName(),returned.getName());
        assertEquals(expected.getAmount(),returned.getAmount());
        assertEquals(expected.getDescription(),returned.getDescription());
        assertEquals(expected.getClass(),returned.getClass());
    }
    private void addAllExpenses() {
        expenseReport.addExpense("FoodExpense1", 100, "D1", 1);
        expenseReport.addExpense("HealthExpense1", 738, "D3", 2);
        expenseReport.addExpense("HousingExpense1", 25, "D5", 3);
        expenseReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        expenseReport.addEasyExpense("PersonalExpense1", 89, "D9", 5);
    }

    private void initializeExpenses() {
        expense1 = new FoodExpense("FoodExpense1", 100, "D1");
        expense2 = new HealthcareExpense("HealthExpense1", 738, "D3");
        expense3 = new HousingExpense("HousingExpense1", 25, "D5");
        expense4 = new TransportationExpense("TransportationExpense1", 1.50, "D7");
        expense5 = new PersonalExpense("PersonalExpense1", 89, "D9");

    }

}