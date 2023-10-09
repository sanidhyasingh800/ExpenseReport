package model.expenseTests;

import model.expense.FoodExpense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodExpenseTest extends ExpenseTest {


    @Test
    public void testAddFood() {
        ((FoodExpense)testExpense1).addFood("Banana");
        List<String> testList = ((FoodExpense)testExpense1).getFoodItems();
        assertEquals(1, testList.size());
        assertEquals("Banana", testList.get(0));
    }

    @Test
    public void testAddFoodMultiple() {
        ((FoodExpense)testExpense2).addFood("Banana");
        ((FoodExpense)testExpense2).addFood("Eggs");
        List<String> testList = ((FoodExpense)testExpense2).getFoodItems();
        assertEquals(2, testList.size());
        assertEquals("Banana", testList.get(0));
        assertEquals("Eggs", testList.get(1));
    }
}
