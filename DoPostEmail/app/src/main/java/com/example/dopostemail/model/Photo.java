package com.example.dopostemail.model;

import java.io.Serializable;

public class Photo implements Serializable {
    private int id;
    private int path;
    private Contact contact;

    public Photo() {
    }

<<<<<<< HEAD
=======
    public Photo(int id, int path, Contact contact) {
        this.id = id;
        this.path = path;
        this.contact = contact;
    }

>>>>>>> 59a578e113e253a1134a47046c21aefe00625307
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
