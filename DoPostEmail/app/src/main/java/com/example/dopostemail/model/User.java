package com.example.dopostemail.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();

    public User() {}

    public User(Long id, String username, String password, String firstname, String lastname,
                   ArrayList<Account> accounts, ArrayList<Contact> contacts, ArrayList<Tag> tags) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accounts = accounts;
        this.contacts = contacts;
        this.tags = tags;
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void addContact(Contact con){
        contacts.add(con);
    }
    public void addAccount(Account acc){
        accounts.add(acc);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ArrayList<Contact> getContacts() {return contacts;}

    public void setContacts(ArrayList<Contact> contacts) {this.contacts = contacts;}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
}
