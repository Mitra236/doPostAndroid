package com.example.dopostemail.model;

public class Photo {
    private int id;
    private String path;
    private Contact contact;

    public Photo() {
    }

    public Photo(int id, String path, Contact contact) {
        this.id = id;
        this.path = path;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
