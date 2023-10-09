package model.expenseTests;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalExpenseTest extends ExpenseTest {

    @BeforeEach
    public void setUp() {
        testExpense1 = new PersonalExpense("testExpense", 100, "testDescription");
        testExpense2 = new PersonalExpense("testExpense2", 150, "testDescription2", 2023, 10, 9);
    }

    @Test
    public void testSetNeed(){
        ((PersonalExpense)testExpense1).setAsNeed(true);
        assertTrue(((PersonalExpense) testExpense1).isNeed());
    }

    @Test
    public void testSetWant(){
        ((PersonalExpense)testExpense1).setAsNeed(false);
        assertFalse(((PersonalExpense) testExpense1).isNeed());
    }
}