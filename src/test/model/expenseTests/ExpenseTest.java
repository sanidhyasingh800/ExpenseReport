package model.expenseTests;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ExpenseTest {
    Expense testExpense1;
    Expense testExpense2;

    @BeforeEach
    public void setUp() {
        testExpense1 = new FoodExpense("testExpense", 100, "testDescription");
        testExpense2 = new FoodExpense("testExpense2", 150, "testDescription2", 2023, 10, 9);
    }

    @Test
    public void testConstructorWithNoDate() {
        assertEquals("testExpense", testExpense1.getName());
        assertEquals(100, testExpense1.getAmount());
        assertEquals("testDescription", testExpense1.getDescription());
        assertEquals(LocalDate.now(), testExpense1.getDateOfCreation());
    }

    @Test
    public void testConstructorWithDate() {
        assertEquals("testExpense", testExpense1.getName());
        assertEquals(100, testExpense1.getAmount());
        assertEquals("testDescription", testExpense1.getDescription());
        assertEquals(2023, testExpense2.getDateOfCreation().getYear());
        assertEquals(10, testExpense2.getDateOfCreation().getMonthValue());
        assertEquals(9, testExpense2.getDateOfCreation().getDayOfMonth());
    }

    @Test
    public void testChangeExpense() {
        testExpense1.changeExpense(160);
        assertEquals(160, testExpense1.getAmount());
    }
}