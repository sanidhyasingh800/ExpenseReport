package model;

import model.expense.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsReportTest {
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
    private Expense expense11;
    private Expense expense12;
    private final double budget = 1000;
    private ExpenseReport testExpenses;
    private ExpenseReport testExpensesEmpty;
    private StatisticsReport testStatistics;
    private StatisticsReport testStatisticsEmpty;

    @BeforeEach
    public void setup() {
        initializeExpenses();
        testExpenses = new ExpenseReport(budget);
        testExpensesEmpty = new ExpenseReport(budget);
        addAllExpenses();
        testStatistics = new StatisticsReport(testExpenses);
        testStatisticsEmpty = new StatisticsReport(testExpensesEmpty);
    }

    @Test
    public void testConstructor() {
        assertEquals(testExpenses, testStatistics.getExpenseReport());
    }

    @Test
    public void testTotalSpending() {
        assertEquals(1317.29, testStatistics.totalSpending(), 0.5);
    }

    @Test
    public void testBudgetLeft() {
        assertEquals(-317.29, testStatistics.budgetLeft(), 0.5);
    }

    @Test
    public void testTotalSpendingByCategory1() {
        assertEquals(250.0, testStatistics.totalSpendingByCategory(1), 0.5);
    }

    @Test
    public void testTotalSpendingByCategory2() {
        assertEquals(288.0, testStatistics.totalSpendingByCategory(2), 0.5);
    }

    @Test
    public void testTotalSpendingByCategory3() {
        assertEquals(175.0, testStatistics.totalSpendingByCategory(3), 0.5);
    }

    @Test
    public void testTotalSpendingByCategory4() {
        assertEquals(65.29, testStatistics.totalSpendingByCategory(4), 0.5);
    }

    @Test
    public void testTotalSpendingByCategory5() {
        assertEquals(539.0, testStatistics.totalSpendingByCategory(5), 0.5);
    }

    @Test
    public void testPercentageOfCategory1NoneReturned() {
        assertEquals(0, testStatisticsEmpty.percentageOfCategory(1));
    }

    @Test
    public void testPercentageOfCategory1() {
        assertEquals(18.97, testStatistics.percentageOfCategory(1), 0.5);
    }

    @Test
    public void testAverageCost() {
        assertEquals(131.72, testStatistics.averageCost(), 0.5);
    }

    @Test
    public void testAverageCostByCategory1() {
        assertEquals(125.0, testStatistics.averageCostByCategory(1), 0.5);
    }

    @Test
    public void testAverageCostByCategory1IsZero() {
        assertEquals(0, testStatisticsEmpty.averageCostByCategory(1));
        assertEquals(0, testStatisticsEmpty.averageCostByCategory(2));
        assertEquals(0, testStatisticsEmpty.averageCostByCategory(3));
        assertEquals(0, testStatisticsEmpty.averageCostByCategory(4));
        assertEquals(0, testStatisticsEmpty.averageCostByCategory(5));
    }
    @Test
    public void testTotalExpenses() {
        assertEquals(10, testStatistics.totalExpenses(), 0.5);
    }

    @Test

    public void testTotalExpensesByCategory() {
        assertEquals(2, testStatistics.totalExpensesInCategory(1));
        assertEquals(2, testStatistics.totalExpensesInCategory(2));
        assertEquals(2, testStatistics.totalExpensesInCategory(3));
        assertEquals(2, testStatistics.totalExpensesInCategory(4));
        assertEquals(2, testStatistics.totalExpensesInCategory(5));
    }

    @Test
    public void testTotalExpensesToday() {
        assertEquals(5, testStatistics.totalExpensesToday(), 0.5);
    }

    @Test
    public void testTotalExpensesWeekly() {
        assertEquals(7, testStatistics.totalExpensesWeekly(), 0.5);
    }

    @Test
    public void testTotalExpensesMonthly() {
        assertEquals(8, testStatistics.totalExpensesMonthly(), 0.5);
    }

    @Test
    public void testTotalSpendingToday() {
        assertEquals(453.5, testStatistics.totalSpendingToday(), 0.5);
    }

    @Test
    public void testTotalSpendingWeekly() {
        assertEquals(653.5, testStatistics.totalSpendingWeekly(), 0.5);
    }

    @Test
    public void testTotalSpendingMonthly() {
        assertEquals(803.5, testStatistics.totalSpendingMonthly(), 0.5);
    }

    @Test
    public void testAverageSpendingToday() {
        assertEquals(90.7, testStatistics.averageSpendingToday(), 0.5);
    }

    @Test
    public void testAverageSpendingTodayZero() {
        assertEquals(0, testStatisticsEmpty.averageSpendingToday());
    }

    @Test
    public void testAverageSpendingWeekly() {
        assertEquals(93.35, testStatistics.averageSpendingWeekly(), 0.5);
    }

    @Test
    public void testAverageSpendingWeeklyZero() {
        assertEquals(0, testStatisticsEmpty.averageSpendingWeekly());
    }


    @Test
    public void testAverageSpendingMonthly() {
        assertEquals(100.43, testStatistics.averageSpendingMonthly(), 0.5);
    }

    @Test
    public void testAverageSpendingMonthlyZero() {
        assertEquals(0, testStatisticsEmpty.averageSpendingMonthly());
    }

    @Test
    public void testGetAverageAmountPerFoodItem() {
        assertEquals(41.66, testStatistics.getAverageAmountPerFoodItem(), 0.5);
    }

    @Test
    public void testTotalAmountSaved() {
        assertEquals(500.0, testStatistics.totalAmountSaved(), 0.5);
    }

    @Test
    public void testPercentageCoveredByInsurance() {
        assertEquals(50, testStatistics.percentageCoveredByInsurance(), 0.5);
    }

    @Test
    public void testGetBillsSummary() {
        List<Double> testList = new ArrayList<>(Collections.nCopies(5, 0.0));
        testList.set(0, 25.0);
        List<Double> resultList = testStatistics.getBillsSummary();
        assertEquals(testList.size(), resultList.size());
        assertEquals(testList.get(0), resultList.get(0), 0.5);
        assertEquals(testList.get(1), resultList.get(1), 0.5);
        assertEquals(testList.get(2), resultList.get(2), 0.5);
        assertEquals(testList.get(3), resultList.get(3), 0.5);
        assertEquals(testList.get(4), resultList.get(4), 0.5);

    }

    @Test
    public void testTotalSpentOnGreenEnergy() {
        assertEquals(1.5, testStatistics.totalSpentOnGreenEnergy(), 0.5);
    }

    @Test
    public void testTotalSpentOnPublicTransportation() {
        assertEquals(1.5, testStatistics.totalSpentOnPublicTransportation(), 0.5);
    }


    @Test
    public void testTotalSpentOnPersonalTransportation() {
        assertEquals(1.5,testStatistics.totalSpentOnPersonalTransportation(), 0.5);
    }

    @Test
    public void testTotalAmountOnNeeds() {
        assertEquals(89.0, testStatistics.totalAmountOnNeeds(), 0.5);
    }

    @Test
    public void testTotalAmountOnWants() {
        assertEquals(450.0, testStatistics.totalAmountOnWants(), 0.5);
    }

    private void addAllExpenses() {
        testExpenses.addExpense("FoodExpense1", 100, "D1", 1);
        ((FoodExpense) testExpenses.getExpenses().get(0)).addFood("Food1");
        ((FoodExpense) testExpenses.getExpenses().get(0)).addFood("Food2");
        ((FoodExpense) testExpenses.getExpenses().get(0)).addFood("Food3");
        testExpenses.addExpenseWithTime("FoodExpense2", 150, "D2", 1,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(1).getDayOfMonth());
        ((FoodExpense) testExpenses.getExpenses().get(1)).addFood("Food4");
        ((FoodExpense) testExpenses.getExpenses().get(1)).addFood("Food5");
        ((FoodExpense) testExpenses.getExpenses().get(1)).addFood("Food6");
        testExpenses.addExpense("HealthExpense1", 738, "D3", 2);
        ((HealthcareExpense) testExpenses.getExpenses().get(2)).coveredByInsurance(500);
        testExpenses.addExpenseWithTime("HealthExpense2", 50, "D4", 2,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(6).getDayOfMonth());
        testExpenses.addExpense("HousingExpense1", 25, "D5", 3);
        ((HousingExpense) testExpenses.getExpenses().get(4)).setTypeofBill(1);
        testExpenses.addExpenseWithTime("HousingExpense2", 150, "D6", 3,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                1);
        testExpenses.addExpense("TransportationExpense1", 1.50, "D7",4 );
        ((TransportationExpense) testExpenses.getExpenses().get(6)).setTypeOfTransportation(1);
        testExpenses.addExpenseWithTime("TransportationExpense2", 63.79, "D8", 4,
                LocalDate.now().minusYears(1).getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());
        testExpenses.addExpense("PersonalExpense1", 89, "D9", 5);
        ((PersonalExpense) testExpenses.getExpenses().get(8)).setAsNeed(true);
        testExpenses.addExpenseWithTime("PersonalExpense2", 450, "D10", 5,
                LocalDate.now().minusDays(31).getYear(),
                LocalDate.now().minusDays(31).getMonthValue(),
                LocalDate.now().minusDays(31).getDayOfMonth());
        testExpenses.addExpense("TransportationExpense3", 1.50, "D11",4 );
        ((TransportationExpense) testExpenses.getExpenses().get(10)).setTypeOfTransportation(2);
        testExpenses.addExpense("TransportationExpense4", 1.50, "D12",4 );
        ((TransportationExpense) testExpenses.getExpenses().get(11)).setTypeOfTransportation(3);
    }

    private void initializeExpenses() {
        expense1 = new FoodExpense("FoodExpense1", 100, "D1");
        ((FoodExpense)expense1).addFood("Food1");
        ((FoodExpense)expense1).addFood("Food2");
        ((FoodExpense)expense1).addFood("Food3");
        expense2 = new FoodExpense("FoodExpense2", 150, "D2",
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(1).getDayOfMonth());
        ((FoodExpense)expense2).addFood("Food4");
        ((FoodExpense)expense2).addFood("Food5");
        ((FoodExpense)expense2).addFood("Food6");
        expense3 = new HealthcareExpense("HealthExpense1", 738, "D3");
        ((HealthcareExpense)expense3).coveredByInsurance(500);
        expense4 = new HealthcareExpense("HealthExpense2", 50, "D4",
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(6).getDayOfMonth());
        expense5 = new HousingExpense("HousingExpense1", 25, "D5");
        ((HousingExpense)expense5).setTypeofBill(1);
        expense6 = new HousingExpense("HousingExpense2", 150, "D6",
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().minusDays(8).getDayOfMonth());
        expense7 = new TransportationExpense("TransportationExpense1", 1.50, "D7");
        ((TransportationExpense) expense7 ).setTypeOfTransportation(1);
        expense8 = new TransportationExpense("TransportationExpense2", 63.79, "D8",
                LocalDate.now().minusYears(1).getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());
        expense9 = new PersonalExpense("PersonalExpense1", 89, "D9");
        ((PersonalExpense) expense9).setAsNeed(true);
        expense10 = new PersonalExpense("PersonalExpense2", 450, "D10",
                LocalDate.now().minusDays(31).getYear(),
                LocalDate.now().minusDays(31).getMonthValue(),
                LocalDate.now().minusDays(31).getDayOfMonth());
        expense11 = new TransportationExpense("TransportationExpense3", 1.50, "D11");
        ((TransportationExpense) expense7 ).setTypeOfTransportation(2);
        expense12 = new TransportationExpense("TransportationExpense3", 1.50, "D12");
        ((TransportationExpense) expense7 ).setTypeOfTransportation(3);
    }
}
