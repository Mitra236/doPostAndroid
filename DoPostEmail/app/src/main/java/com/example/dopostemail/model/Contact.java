package com.example.dopostemail.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable, Cloneable {

    private Long id;
    private String firstName;
    private String lastName;
    private String display;
    private String email;
    private Format format;
    private Photo photo;
    private User user;
    private ArrayList<Message> from;
    private ArrayList<Message> to;
    private ArrayList<Message> cc;
    private ArrayList<Message> bcc;

    public Contact(Long id, String firstName, String lastName, String display, String email, Format format, Photo photo, User user, ArrayList<Message> from, ArrayList<Message> to, ArrayList<Message> cc, ArrayList<Message> bcc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
        this.user = user;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    public Contact(Long id, String firstName, String lastName, String display, String email, Format format, Photo photo, ArrayList<Message> from, ArrayList<Message> to, ArrayList<Message> cc, ArrayList<Message> bcc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    public Contact(String display, String email, String firstName, Format format, String lastName){
        this.display = display;
        this.email = email;
        this.firstName = firstName;
        this.format = format;
      //  this.id = id;
        this.lastName = lastName;
    }

//    public Contact(int id, String firstName, String lastName, String display, String email, Format format, ArrayList<Message> from, ArrayList<Message> to, ArrayList<Message> cc, ArrayList<Message> bcc) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.display = display;
//        this.email = email;
//        this.format = format;
//        this.from = from;
//        this.to = to;
//        this.cc = cc;
//        this.bcc = bcc;
//        this.photo = photo;
//    }

    public Contact(Long id, String firstName, String lastName, String display, String email, Format format, Photo photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
    }

    public Contact(String firstName, String lastName, String display, String email, Format format, Photo photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
    }

    public Contact(Long id, String firstName, String lastName, String display, String email, Format format) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.display = display;
        this.email = email;
        this.format = format;
    }

    public Contact(String firstName){
        this.firstName = firstName;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public Object clone()throws CloneNotSupportedException {
        return (Contact) super.clone();
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + " " + email;
    }

}
