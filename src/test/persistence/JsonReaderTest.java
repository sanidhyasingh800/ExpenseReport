package persistence;

import model.ExpenseReport;
import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private Expense expense5;


    @BeforeEach
    void setup() {
        initializeExpenses();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ExpenseReport expenseReport = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderReportNoExpenses() {
        JsonReader reader = new JsonReader("./data/testReaderReportNoExpenses.json");
        try {
            ExpenseReport expenseReport = reader.read();
            assertEquals("Sunny", expenseReport.getName());
            assertEquals(100, expenseReport.getBudget());
            assertEquals(0, expenseReport.getExpenses().size());
            assertEquals(0, expenseReport.getEasyAdd().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralExpenses() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralExpenses.json");
        try {
            ExpenseReport expenseReport = reader.read();
            assertEquals("Sunny", expenseReport.getName());
            assertEquals(100, expenseReport.getBudget());
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
            fail("Couldn't read from file");
        }
    }

    private void checkForCorrectExpense(Expense expected, Expense returned) {
        assertEquals(expected.getName(),returned.getName());
        assertEquals(expected.getAmount(),returned.getAmount());
        assertEquals(expected.getDescription(),returned.getDescription());
        assertEquals(expected.getClass(),returned.getClass());
    }

    private void initializeExpenses() {
        expense1 = new FoodExpense("FoodExpense1", 100, "D1");
        ((FoodExpense) expense1).addFood("Milk");
        ((FoodExpense) expense1).addFood("Bread");
        expense2 = new HealthcareExpense("HealthExpense1", 738, "D3");
        expense3 = new HousingExpense("HousingExpense1", 25, "D5");
        expense4 = new TransportationExpense("TransportationExpense1", 1.50, "D7");
        expense5 = new PersonalExpense("PersonalExpense1", 89, "D9");

    }
}