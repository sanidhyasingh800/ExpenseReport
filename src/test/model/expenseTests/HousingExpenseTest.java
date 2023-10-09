package model.expenseTests;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HousingExpenseTest extends ExpenseTest {
    @BeforeEach
    public void setUp() {
        testExpense1 = new HousingExpense("testExpense", 100, "testDescription");
        testExpense2 = new HousingExpense("testExpense2", 150, "testDescription2", 2023, 10, 9);
    }

    @Test
    public void testSetBill(){
        ((HousingExpense)testExpense1).setTypeofBill(1);
        ((HousingExpense)testExpense2).setTypeofBill(2);
        assertEquals(1, ((HousingExpense)testExpense1).getTypeofBill());
        assertEquals(2, ((HousingExpense)testExpense2).getTypeofBill());
    }
}
