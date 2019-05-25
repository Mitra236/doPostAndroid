package com.example.dopostemail.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Folder implements Serializable {

    private int id;
    private String name;
    private ArrayList<Folder> folders;
    private ArrayList<Message> messages;
    private Rule rule;

    public Folder(int id, String name, ArrayList<Folder> folders, ArrayList<Message> messages, Rule rule) {
        this.id = id;
        this.name = name;
        this.folders = folders;
        this.messages = messages;
        this.rule = rule;
    }

    public Folder(String name, ArrayList<Folder> folders, ArrayList<Message> messages, Rule rule) {

        this.name = name;
        this.folders = folders;
        this.messages = messages;
        this.rule = rule;
    }

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
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

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public Rule getRule() {
        return rule;
    }


    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
