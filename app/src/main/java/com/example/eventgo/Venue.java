package com.example.eventgo;

public class Venue {
    public String name;
    public String location;
    public String image;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Venue(String name, String location, String image, String type, int budget, int guest) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.type = type;
        this.budget = budget;
        this.guest = guest;
    }

    public String type;
    public int budget;
    public int guest;

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public Venue(String name, String location, String image, int budget, int guest) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.budget = budget;
        this.guest = guest;
    }

    public Venue()
    {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Venue(String name, String location, String image, int budget) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.budget = budget;
    }
}
