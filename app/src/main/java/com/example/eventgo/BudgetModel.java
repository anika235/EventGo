package com.example.eventgo;

public class BudgetModel {
    String item,date,id,note;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BudgetModel(String item, String date, String id, String note, int amount, int month) {
        this.item = item;
        this.date = date;
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.month = month;
    }

    public BudgetModel() {
    }

    int amount,month;
}
