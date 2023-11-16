package ui;

import model.ExpenseReport;
import ui.swing.ExpenseReportUI;

public class Main {
    public static void main(String[] args) {
        ExpenseReportUI app = new ExpenseReportUI(new ExpenseReport("Test", 1000));
        System.out.println("done");

    }
}
