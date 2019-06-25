package com.example.dopostemail.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Folder parentFolder;
    private ArrayList<Message> messages;
    private ArrayList<Rule> rule;


    public Folder(Long id, String name, Folder parentFolder, ArrayList<Message> messages, ArrayList<Rule> rule) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.messages = messages;
        this.rule = rule;
    }

    public Folder(String name, Folder parentFolder, ArrayList<Message> messages, ArrayList<Rule> rule) {
        super();

        this.name = name;
        this.parentFolder = parentFolder;
        this.messages = messages;
        this.rule = rule;
    }


    public Folder(){
        super();

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
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


    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Rule> getRule() {
        return rule;
    }

    public void setRule(ArrayList<Rule> rule) {
        this.rule = rule;
    }
}
