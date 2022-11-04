package com.example.eventgo;

public class BudgetModel {
    int ExpenseId;
    String ExpenseName;
    String ExpenseMoney;

    public BudgetModel(int expenseId, String expenseName, String expenseMoney) {
        ExpenseId = expenseId;
        ExpenseName = expenseName;
        ExpenseMoney = expenseMoney;
    }

    public int getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(int expenseId) {
        ExpenseId = expenseId;
    }

    public String getExpenseName() {
        return ExpenseName;
    }

    public void setExpenseName(String expenseName) {
        ExpenseName = expenseName;
    }

    public String getExpenseMoney() {
        return ExpenseMoney;
    }

    public void setExpenseMoney(String expenseMoney) {
        ExpenseMoney = expenseMoney;
    }

    public BudgetModel(int expenseId, String expenseName) {
        ExpenseId = expenseId;
        ExpenseName = expenseName;
    }
}
