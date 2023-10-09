package model;

import model.expense.*;
import model.ExpenseReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseReportTest {
    private Expense expense1;
    private Expense expense2;  // represents expense yesterday
    private Expense expense3;
    private Expense expense4;  // represents expense less than 7 days ago
    private Expense expense5;
    private Expense expense6;  // represents expense more than 7 days ago
    private Expense expense7;
    private Expense expense8;  // represents expense made on 1st of month
    private Expense expense9;
    private Expense expense10; // represents expense more than a month ago
    private double budget = 1000;
    private ExpenseReport testReport;

    @BeforeEach
    public void setUp() {
        initializeExpenses();
        testReport = new ExpenseReport(1000);
    }

    @Test
    public void testConstructor(){
        assertEquals(1000, testReport.getBudget());
        assertEquals(0, testReport.getExpenses().size());

    }


    @Test
    public void testAddExpenseFoodExpense() {
        testReport.addExpense("FoodExpense1", 100, "D1", 1);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense1, test);

    }

    @Test
    public void testAddExpenseHealthcareExpense() {
        testReport.addExpense("HealthExpense1", 738, "D3", 2);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense3, test);

    }

    @Test
    public void testAddExpenseHousingExpense() {
        testReport.addExpense("HousingExpense1", 25, "D5", 3);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense5, test);

    }

    @Test
    public void testAddExpenseTransportationExpense() {
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testAddExpensePersonalExpense() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense9, test);

    }

    @Test
    public void testAddMultipleExpenses() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpenses();
        assertEquals(3, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(expense9, test);
        test = testReport.getExpenses().get(1);
        checkForCorrectExpense(expense7, test);
        test = testReport.getExpenses().get(2);
        checkForCorrectExpense(expense7, test);

    }


    @Test
    public void testAddExpenseFoodExpenseToEasyAdd() {
        testReport.addEasyExpense("FoodExpense1", 100, "D1", 1);
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense1, test);

    }

    @Test
    public void testAddExpenseHealthcareExpenseToEasyAdd() {
        testReport.addEasyExpense("HealthExpense1", 738, "D3", 2);
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense3, test);

    }

    @Test
    public void testAddExpenseHousingExpenseToEasyAdd() {
        testReport.addEasyExpense("HousingExpense1", 25, "D5", 3);
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense5, test);

    }

    @Test
    public void testAddExpenseTransportationExpenseToEasyAdd() {
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(1, testList.size());
        Expense test =  testList .get(0);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testAddExpensePersonalExpenseToEasyAdd() {
        testReport.addEasyExpense("PersonalExpense1", 89, "D9", 5);
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);

    }

    @Test
    public void testAddMultipleExpensesToEasyAdd() {
        testReport.addEasyExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(3, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense7, test);
        test = testList.get(2);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testAddSavedExpenseToExpenseReport() {
        testReport.addEasyExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addEasyExpenseToReport(0);
        testReport.addEasyExpenseToReport(1);
        testReport.addEasyExpenseToReport(2);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(3, testList.size());
        Expense test = testReport.getExpenses().get(0);
        checkForCorrectExpense(testReport.getEasyAdd().get(0), test);
        test = testReport.getExpenses().get(1);
        checkForCorrectExpense(testReport.getEasyAdd().get(1), test);
        test = testReport.getExpenses().get(2);
        checkForCorrectExpense(testReport.getEasyAdd().get(2), test);
    }


    @Test
    public void testRemoveExpenseAtGivenIndexFromExpenseReport() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.removeExpense(1);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testRemoveExpenseAtGivenIndexFromSavedExpenses() {
        testReport.addEasyExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addEasyExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.removeEasyAddExpense(1);
        List<Expense> testList = testReport.getEasyAdd();
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense7, test);
    }

    @Test
    public void testRemoveExpenseUsingNameFromExpenseReportNoExpensesRemoved() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.removeExpenses("TransportationExpense2");
        List<Expense> testList = testReport.getExpenses();
        assertEquals(3, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense7, test);
        test = testList.get(2);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testRemoveExpenseUsingNameFromExpenseReport() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.removeExpenses("TransportationExpense1");
        List<Expense> testList = testReport.getExpenses();
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);

    }

    @Test
    public void testGetExpensesAboveAmountNoneReturned() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpensesAboveAmount(90);
        assertEquals(0, testList.size());

    }

    @Test
    public void testGetExpensesAboveAmount() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpensesAboveAmount(89);
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);

    }


    // Private Helpers

    private void checkForCorrectExpense(Expense expected, Expense returned) {
        assertEquals(expected.getName(),returned.getName());
        assertEquals(expected.getAmount(),returned.getAmount());
        assertEquals(expected.getDescription(),returned.getDescription());
        assertEquals(expected.getClass(),returned.getClass());
    }

    private void addAllExpenses() {
        testReport.addExpense("FoodExpense1", 100, "D1", 1);
        testReport.addExpenseWithTime("FoodExpense2", 150, "D1", 1,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(1).getDayOfMonth());
        testReport.addExpense("HealthExpense1", 738, "D3", 2);
        testReport.addExpenseWithTime("HealthExpense2", 50, "D3", 2,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(6).getDayOfMonth());
        testReport.addExpense("HousingExpense1", 25, "D5", 3);
        testReport.addExpenseWithTime("HousingExpense2", 150, "D5", 3,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                1);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpenseWithTime("TransportationExpense2", 63.79, "D7", 4,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), 1);
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpenseWithTime("PersonalExpense2", 450, "D9", 5,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue() -1,
                LocalDate.now().getDayOfMonth());
    }

    private void initializeExpenses() {
        expense1 = new FoodExpense("FoodExpense1", 100, "D1");
        expense2 = new FoodExpense("FoodExpense2", 150, "D1",
                                    LocalDate.now().getYear(),
                                    LocalDate.now().getMonthValue(),
                                    LocalDate.now().minusDays(1).getDayOfMonth());
        expense3 = new HealthcareExpense("HealthExpense1", 738, "D3");
        expense4 = new HealthcareExpense("HealthExpense2", 50, "D3",
                                        LocalDate.now().getYear(),
                                        LocalDate.now().getMonthValue(),
                                        LocalDate.now().minusDays(6).getDayOfMonth());
        expense5 = new HousingExpense("HousingExpense1", 25, "D5");
        expense6 = new HousingExpense("HousingExpense2", 150, "D5",
                                    LocalDate.now().getYear(),
                                    LocalDate.now().getMonthValue(),
                                    1);
        expense7 = new TransportationExpense("TransportationExpense1", 1.50, "D7");
        expense8 = new TransportationExpense("TransportationExpense2", 63.79, "D7",
                                            LocalDate.now().getYear(),
                                            LocalDate.now().getMonthValue(), 1);
        expense9 = new PersonalExpense("PersonalExpense1", 89, "D9");
        expense10 = new PersonalExpense("PersonalExpense2", 450, "D9",
                                        LocalDate.now().getYear(),
                                        LocalDate.now().getMonthValue() -1,
                                        LocalDate.now().getDayOfMonth());
    }

}
