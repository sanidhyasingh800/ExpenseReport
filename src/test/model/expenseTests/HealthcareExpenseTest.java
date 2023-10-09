package model.expenseTests;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthcareExpenseTest extends ExpenseTest{

    @BeforeEach
    public void setUp() {
        testExpense1 = new HealthcareExpense("testExpense", 100, "testDescription");
        testExpense2 = new HealthcareExpense("testExpense2", 150, "testDescription2", 2023, 10, 9);

    }

    @Test
    public void testCoveredByInsurance() {
        ((HealthcareExpense) testExpense1).coveredByInsurance(50);
        ((HealthcareExpense) testExpense2).coveredByInsurance(150);
        assertEquals(50, ((HealthcareExpense)testExpense1).getAmountCovered());
        assertEquals(50, testExpense1.getAmount());
        assertEquals(150, ((HealthcareExpense)testExpense2).getAmountCovered());
        assertEquals(0, testExpense2.getAmount());
    }
}
