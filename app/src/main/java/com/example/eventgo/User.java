package com.example.eventgo;

public class User {
    public String fname,lname,mail,number,bdate;

    public User(String fname, String lname, String mail,String number, String bdate) {
        this.fname = fname;
        this.lname = lname;
        this.mail=mail;
        this.number = number;
        this.bdate = bdate;
    }

    public User() {
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return this.mail;
    }

    public String getNumber() {
        return this.number;
    }

    public String getBdate() {
        return this.bdate;
    }

    public String getLname() {
        return this.lname;
    }

}
