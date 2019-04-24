package com.example.dopostemail.model;

public class Photo {
    private int id;
    private int path;
    private Contact contact;

    public Photo() {
    }

    public Photo(int id, int path, Contact contact) {
        this.id = id;
        this.path = path;
        this.contact = contact;
    }

    public Photo(int id, int path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
