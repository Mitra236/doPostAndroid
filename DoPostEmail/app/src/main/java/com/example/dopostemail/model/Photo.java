package com.example.dopostemail.model;

import java.io.Serializable;

public class Photo implements Serializable {
    private Long id;
    private String path;
    private Contact contact;

    public Photo() {
    }

    public Photo(Long id, String path, Contact contact) {
        this.id = id;
        this.path = path;
        this.contact = contact;
    }

    public Photo(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Photo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
