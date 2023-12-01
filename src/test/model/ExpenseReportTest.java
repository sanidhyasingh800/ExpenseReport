package model;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseReportTest {
    private Expense expense1;
    private Expense expense2;  // represents expense yesterday
    private Expense expense3;
    private Expense expense4;  // represents expense made 6 days ago
    private Expense expense5;
    private Expense expense6;  // represents expense made 8 days ago
    private Expense expense7;
    private Expense expense8;  // represents expense made last year
    private Expense expense9;
    private Expense expense10; // represents expense more than a month ago
    private final double budget = 1000;
    private ExpenseReport testReport;

    @BeforeEach
    public void setUp() {
        initializeExpenses();
        testReport = new ExpenseReport("reportName", budget);
    }

    @Test
    public void testConstructor(){
        assertEquals(1000, testReport.getBudget());
        assertEquals("reportName", testReport.getName());
        assertEquals(0, testReport.getExpenses().size());

    }

    @Test
    public void testAddExpenseIncorrectCase() {
        testReport.addExpense("FoodExpense1", 100, "D1", 6);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(0, testList.size());

    }

    @Test
    public void testAddExpenseWithTimeIncorrectCase() {
        testReport.addExpenseWithTime("FoodExpense1", 100, "D1", 6, 2023, 10, 9);
        List<Expense> testList = testReport.getExpenses();
        assertEquals(0, testList.size());

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

    @Test
    public void testGetExpensesBelowAmountNoneReturned() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpensesBelowAmount(1);
        assertEquals(0, testList.size());

    }

    @Test
    public void testGetExpensesBelowAmount() {
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        List<Expense> testList = testReport.getExpensesBelowAmount(89);
        assertEquals(3, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense7, test);
        test = testList.get(2);
        checkForCorrectExpense(expense7, test);

    }

    @Test
    public void testGetSpecificCategoryOfExpensesNoneReturned() {
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(1);
        assertEquals(0, testList.size());
    }

    @Test
    public void testGetFoodExpenses() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(1);
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense1, test);
        test = testList.get(1);
        checkForCorrectExpense(expense2, test);
    }

    @Test
    public void testGetHealthcareExpenses() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(2);
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense3, test);
        test = testList.get(1);
        checkForCorrectExpense(expense4, test);
    }

    @Test
    public void testGetHousingExpenses() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(3);
        assertEquals(1, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense5, test);
    }

    @Test
    public void testGetTransportationExpenses() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(4);
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense7, test);
        test = testList.get(1);
        checkForCorrectExpense(expense8, test);
    }

    @Test
    public void testGetPersonalExpenses() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(5);
        assertEquals(2, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense9, test);
        test = testList.get(1);
        checkForCorrectExpense(expense10, test);
    }

    @Test
    public void testGetSpecificCategoryButWrongIndexProvided() {
        addAllExpenses();
        List<Expense> testList = testReport.getSpecificCategoryOfExpense(6);
        assertEquals(0, testList.size());
    }

    @Test
    public void testFilterByDayNoneReturned() {
        List<Expense> testList = testReport.filterByDay();
        assertEquals(0, testList.size());
    }

    @Test
    public void testFilterByDay() {
        addAllExpenses();
        List<Expense> testList = testReport.filterByDay();
        assertEquals(5, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense1, test);
        test = testList.get(1);
        checkForCorrectExpense(expense3, test);
        test = testList.get(2);
        checkForCorrectExpense(expense5, test);
        test = testList.get(3);
        checkForCorrectExpense(expense7, test);
        test = testList.get(4);
        checkForCorrectExpense(expense9, test);
    }

    @Test
    public void testFilterByWeekNoneReturned() {
        List<Expense> testList = testReport.filterByWeek();
        assertEquals(0, testList.size());
    }

    @Test
    public void testFilterByWeek() {
        addAllExpenses();
        List<Expense> testList = testReport.filterByWeek();
        Expense test = testList.get(0);
        assertEquals(7, testList.size());
        checkForCorrectExpense(expense1, test);
        test = testList.get(1);
        checkForCorrectExpense(expense2, test);
        test = testList.get(2);
        checkForCorrectExpense(expense3, test);
        test = testList.get(3);
        checkForCorrectExpense(expense4, test);
        test = testList.get(4);
        checkForCorrectExpense(expense5, test);
        test = testList.get(5);
        checkForCorrectExpense(expense7, test);
        test = testList.get(6);
        checkForCorrectExpense(expense9, test);
    }

    @Test
    public void testFilterByMonthNoneReturned() {
        List<Expense> testList = testReport.filterByMonth();
        assertEquals(0, testList.size());
    }

    @Test
    public void testFilterByMonth() {
        addAllExpenses();
        List<Expense> testList = testReport.filterByMonth();
        assertEquals(7, testList.size());
        Expense test = testList.get(0);
        checkForCorrectExpense(expense1, test);
        test = testList.get(1);
        checkForCorrectExpense(expense2, test);
        test = testList.get(2);
        checkForCorrectExpense(expense3, test);
        test = testList.get(3);
        checkForCorrectExpense(expense4, test);
        test = testList.get(4);
        checkForCorrectExpense(expense5, test);
        test = testList.get(5);
        checkForCorrectExpense(expense7, test);
        test = testList.get(6);
        checkForCorrectExpense(expense9, test);
    }


    @Test
    public void testChangeBudget() {
        testReport.changeBudget(2000);
        assertEquals(2000, testReport.getBudget());
    }

    @Test
    public void testGetMostRecent() {
        addAllExpenses();
        checkForCorrectExpense(expense10, testReport.getMostRecent());
    }

    @Test
    public void testGetMostRecentSavedExpense() {
        testReport.addEasyExpense("HealthExpense1", 738, "D3", 2);
        checkForCorrectExpense(expense3, testReport.getMostRecentEasyAddExpense());
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
        testReport.addExpenseWithTime("FoodExpense2", 150, "D2", 1,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(1).getDayOfMonth());
        testReport.addExpense("HealthExpense1", 738, "D3", 2);
        testReport.addExpenseWithTime("HealthExpense2", 50, "D4", 2,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(6).getDayOfMonth());
        testReport.addExpense("HousingExpense1", 25, "D5", 3);
        // testReport.addExpenseWithTime("HousingExpense2", 150, "D6", 3,
                //LocalDate.now().getYear(),
               // LocalDate.now().getMonthValue(),
               // 1);
        testReport.addExpense("TransportationExpense1", 1.50, "D7",4 );
        testReport.addExpenseWithTime("TransportationExpense2", 63.79, "D8", 4,
                LocalDate.now().minusYears(1).getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());
        testReport.addExpense("PersonalExpense1", 89, "D9", 5);
        testReport.addExpenseWithTime("PersonalExpense2", 450, "D10", 5,
                LocalDate.now().minusDays(31).getYear(),
                LocalDate.now().minusDays(31).getMonthValue(),
                LocalDate.now().minusDays(31).getDayOfMonth());
    }

    private void initializeExpenses() {
        expense1 = new FoodExpense("FoodExpense1", 100, "D1");
        expense2 = new FoodExpense("FoodExpense2", 150, "D2",
                                    LocalDate.now().getYear(),
                                    LocalDate.now().getMonthValue(),
                                    LocalDate.now().minusDays(1).getDayOfMonth());
        expense3 = new HealthcareExpense("HealthExpense1", 738, "D3");
        expense4 = new HealthcareExpense("HealthExpense2", 50, "D4",
                                        LocalDate.now().getYear(),
                                        LocalDate.now().getMonthValue(),
                                        LocalDate.now().minusDays(6).getDayOfMonth());
        expense5 = new HousingExpense("HousingExpense1", 25, "D5");
        expense6 = new HousingExpense("HousingExpense2", 150, "D6",
                                    LocalDate.now().getYear(),
                                    LocalDate.now().getMonthValue(),
                                    LocalDate.now().minusDays(8).getDayOfMonth());
        expense7 = new TransportationExpense("TransportationExpense1", 1.50, "D7");
        expense8 = new TransportationExpense("TransportationExpense2", 63.79, "D8",
                                            LocalDate.now().minusYears(1).getYear(),
                                            LocalDate.now().getMonthValue(),
                                            LocalDate.now().getDayOfMonth());
        expense9 = new PersonalExpense("PersonalExpense1", 89, "D9");
        expense10 = new PersonalExpense("PersonalExpense2", 450, "D10",
                                        LocalDate.now().minusDays(31).getYear(),
                                        LocalDate.now().minusDays(31).getMonthValue(),
                                        LocalDate.now().minusDays(31).getDayOfMonth());
    }

}
