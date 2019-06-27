package com.example.dopostemail.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Folder parentFolder;
    private ArrayList<Folder> folders;
    private ArrayList<Message> messages;
    private ArrayList<Rule> rule;
    private Account account;


    public Folder(Long id, String name, Folder parentFolder, ArrayList<Message> messages, ArrayList<Rule> rule, Account account, ArrayList<Folder> folders) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.messages = messages;
        this.rule = rule;
        this.account = account;
        this.folders = folders;
    }

    public Folder(Long id, String name, Folder parentFolder, ArrayList<Message> messages, ArrayList<Rule> rule, Account account) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.messages = messages;
        this.rule = rule;
        this.account = account;
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

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }
}
