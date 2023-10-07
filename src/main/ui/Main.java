package ui;

import model.ExpenseReport;

public class Main {
    public static void main(String[] args) {
        ExpenseReport x = new ExpenseReport(100);
        x.addExpense("Grocery", 120, "Bought weeks worth of groceries", 1);


    }
}
