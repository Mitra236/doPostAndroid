package com.example.dopostemail.model;

public class Account {

    private int id;
    private String smtp;
    private String pop3_imap;
    private String username;
    private String password;
    private Message message;

    public Account(int id, String smtp, String pop3_imap, String username, String password, Message message) {
        this.id = id;
        this.smtp = smtp;
        this.pop3_imap = pop3_imap;
        this.username = username;
        this.password = password;
        this.message = message;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPop3_imap() {
        return pop3_imap;
    }

    public void setPop3_imap(String pop3_imap) {
        this.pop3_imap = pop3_imap;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
