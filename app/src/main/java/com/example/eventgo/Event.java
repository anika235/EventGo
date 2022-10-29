package com.example.eventgo;

public class Event {
    public String title;
    public String type;
    public String date;
    public String time;
    public String key;

    public String locate, budget, guest;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocate() { return locate; }

    public String getBudget() { return budget; }

    public String getGuest() {return guest;}

    public String getKey() {
        return key;}

    public void setKey(String key) {
        this.key = key;}

    public Event() {
    }

    public String image;

    public Event(String Title,String type,String date,String time,String key)
    {
        this.title=Title;
        this.type=type;
        this.date=date;
        this.time=time;
        this.key = key;
    }

    public Event(String Locate, String Budget, String Guest)
    {
        this.locate = Locate;
        this.budget = Budget;
        this.guest = Guest;
    }




}
