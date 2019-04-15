package com.example.dopostemail.model;

import java.util.ArrayList;

public class Folder {

    private int id;
    private String name;
    private ArrayList<Folder> folders;
    private Rule rule;

    public Folder(int id, String name, ArrayList<Folder> folders, Rule rule) {
        this.id = id;
        this.name = name;
        this.folders = folders;
        this.rule = rule;
    }

    public Folder(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
