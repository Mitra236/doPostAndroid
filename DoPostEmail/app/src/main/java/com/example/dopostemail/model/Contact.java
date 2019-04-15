package com.example.dopostemail.model;

public class Contact {

    private int id;
    private String firstName;
    private String lastName;
    private String display;
    private String email;
    private String format;

    public Contact(int id, String firstName, String lastName, String display, String email, String format) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
    }

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
