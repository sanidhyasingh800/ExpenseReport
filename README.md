# Finance Tracker

## Project Proposal
I plan to implement a **Finance Tracker** Application for my Personal Project.
Living off campus my second year made me realize how much money we spend on groceries, monthly bills,
transportation, and more. Having a simple application in which we can track our purchases and see basic
statistics about how we are spending our money would be extremely useful for the everyday student.

This application will be catered towards students who want a simple way of having
all purchases and expenses tracked in one place. It will provide users a method to add
different types of expenses such as recurring monthly bills and subscriptions, one time expenses,
and other categories such as food, transportation, and more. Users can see how much money they
are spending over the period of a day, week, or month, as well as what percentage of their spending
is spent in different categories. Users can also add a budget cap to track how much money they have
left over after each expense.


A Detailed View of the Functionalities:
- A way to add different types of expenses
- Label each type of expense
- Set up recurring expenses
- Track amount spent within each category
- Track percentage of money spent on each category over days, weeks, months
- Add a budget cap to the overall Expense Report

## User Stories
- As a user, I want to be able to specify different expense categories such as food, bills, etc.
- As a user, I want to be able to add new expenses and specify cost, name, and category.
- As a user, I want to be able to remove expenses.
- As a user, I want to be able to save recurring expenses that I can add multiple times.
- As a user, I want to be able to view a list of all my expenses for that day, week, or month.
- As a user, I want to be able to view statistics on how much money I spend within each category.
- As a user, I want to be able to track how my expenses change over the period of days, weeks, and months.
- As a user, I want to be able to set a budget cap for my expenses and be able to change it later on.
- As a user, I want to be able to have the option to save my expense report.
- As a user, I want to be able to have the option to load in expense report from file.


## Instructions for Grader

- You can add expenses to the expense report by selecting category, name, cost, and description and pressing "Enter Expense". Click "Refresh" on the table to see the updated report
- You can remove expenses by selecting a row in the overall report view of the table and pressing delete
- You can view different expenses through filters available in the dropdown menu below the table
- You can view different graphs of the data by choosing the graph view from the dropdown graphs menu
- You can save the state the report by clicking save or save and quit 
- You can reload the report by clicking "Load Saved Report" at launch


## Phase 4: Task 2
- Fri Dec 01 08:25:03 PST 2023 
  - Expense Report Created with name: sunny and budget: 1000.0
- Fri Dec 01 08:25:17 PST 2023
  - Food Expense created with name: save ons , cost: 100.0, and description: grocery
- Fri Dec 01 08:25:18 PST 2023
  - Overall Expense Report Viewed
- Fri Dec 01 08:25:35 PST 2023 
  - Healthcare Expense created with name: gympass, cost: 45.0, and description: health
- Fri Dec 01 08:25:36 PST 2023 
  - Overall Expense Report Viewed
- Fri Dec 01 08:25:38 PST 2023 
  - Health Expense Report Viewed
- Fri Dec 01 08:25:40 PST 2023 
  - Food Expense Report Viewed
- Fri Dec 01 08:25:43 PST 2023 
  - Overall Expense Report Viewed
- Fri Dec 01 08:25:45 PST 2023 
  - Deleted Expense with name: gympass and cost: 45.0
- Fri Dec 01 08:25:48 PST 2023 
  - Expense Report: sunny saved to file
  
## Phase 4: Task 3:
Looking back at Phase 1, I decided to create an abstract Expense 
class and then create 5 types of Expenses with specialized behavior.
However, this design choice lead to a lot of code repetition and complication.
An example of this is the addExpense() method in the Expense report, where
I had to use a switch case to be able to add 5 different types of expenses 
as per the user's choice. Furthermore, this also increased coupling as model and ui classes had
to handle correctly adding the right expense and any discrepancy between the two would cause the wrong expense type 
to be added without any warning.

To refactor this, I would instead use a single expense class and instead
store different categories of expenses in a Hashmap in the expense report class. 
Not only would this ensure that adding, deleting, and filtering an expense is easier to implement, 
it could also allow users to add different categories as per their choice. Using a string representation of
category as keys, I could easily add to existing or new categories of expenses. With this change, only expense report 
needs to worry about different categories and the only responsibility of the UI class is to pass through the user input for
category, reducing coupling greatly. 






