package com.example.dopostemail.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String display;
    private String email;
    private Format format;
    private Photo photo;
    private ArrayList<Message> from;
    private ArrayList<Message> to;
    private ArrayList<Message> cc;
    private ArrayList<Message> bcc;

    public Contact(int id, String firstName, String lastName, String display, String email, Format format, Photo photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
    }

    public Contact(int id, String firstName, String lastName, String display, String email, Format format) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
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

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public ArrayList<Message> getFrom() {
        return from;
    }

    public ArrayList<Message> getTo() {
        return to;
    }

    public ArrayList<Message> getCc() {
        return cc;
    }

    public ArrayList<Message> getBcc() {
        return bcc;
    }

    public void setFrom(ArrayList<Message> from) {
        this.from = from;
    }

    public void setTo(ArrayList<Message> to) {
        this.to = to;
    }

    public void setCc(ArrayList<Message> cc) {
        this.cc = cc;
    }

    public void setBcc(ArrayList<Message> bcc) {
        this.bcc = bcc;
    }
}
