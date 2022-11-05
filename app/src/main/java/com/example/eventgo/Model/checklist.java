package com.example.eventgo.Model;

public class checklist {
    private String task, dueDate;
    private String status;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String due) {
        this.dueDate = due;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public checklist(String task, String due, String status) {
        this.task = task;
        this.dueDate = due;
        this.status = status;
    }

    @Override
    public String toString() {
        return "checklist{" +
                "task='" + task + '\'' +
                ", due='" + dueDate + '\'' +
                ", status=" + status +
                '}';
    }

    public checklist() {

    }
}
