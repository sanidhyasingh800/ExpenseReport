package model.expenseTests;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportationExpenseTest extends ExpenseTest {
    @BeforeEach
    public void setUp() {
        testExpense1 = new TransportationExpense("testExpense", 100, "testDescription");
        testExpense2 = new TransportationExpense("testExpense2", 150, "testDescription2", 2023, 10, 9);
    }

    @Test
    public void testSetTransportationType() {
        ((TransportationExpense)testExpense1).setTypeOfTransportation(1);
        ((TransportationExpense)testExpense2).setTypeOfTransportation(2);
        assertEquals(1, ((TransportationExpense)testExpense1).getTypeOfTransportation());
        assertEquals(2, ((TransportationExpense)testExpense2).getTypeOfTransportation());
    }
}