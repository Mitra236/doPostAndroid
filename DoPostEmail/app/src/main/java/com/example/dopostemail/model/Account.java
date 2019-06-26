package com.example.dopostemail.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String smtp_address;
    private Integer smtp_port;
    private Short inserver_type;
    private String inserver_address;
    private Integer inserver_port;
    private String username;
    private String password;
    private String displayname;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private ArrayList<Folder> folders = new ArrayList<Folder>();
    private User user;



    public Account(Long id, String smtp_address, Integer smtp_port, Short inserver_type, String inserver_address,
                      Integer inserver_port, String username, String password, String displayname, ArrayList<Message> messages,
                      ArrayList<Folder> folders, User user) {
        super();
        this.id = id;
        this.smtp_address = smtp_address;
        this.smtp_port = smtp_port;
        this.inserver_type = inserver_type;
        this.inserver_address = inserver_address;
        this.inserver_port = inserver_port;
        this.username = username;
        this.password = password;
        this.displayname = displayname;
        this.messages = messages;
        this.folders = folders;
        this.user = user;
    }


    public Account() {
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addFolder(Folder fol){
        folders.add(fol);
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

    public ArrayList<Message> getMessage() {
        return messages;
    }

    public void setMessage(ArrayList<Message> message) {
        this.messages = message;
    }

    public String getSmtp_address() {
        return smtp_address;
    }

    public void setSmtp_address(String smtp_address) {
        this.smtp_address = smtp_address;
    }

    public Integer getSmtp_port() {
        return smtp_port;
    }

    public void setSmtp_port(Integer smtp_port) {
        this.smtp_port = smtp_port;
    }

    public Short getInserver_type() {
        return inserver_type;
    }

    public void setInserver_type(Short inserver_type) {
        this.inserver_type = inserver_type;
    }

    public String getInserver_address() {
        return inserver_address;
    }

    public void setInserver_address(String inserver_address) {
        this.inserver_address = inserver_address;
    }

    public Integer getInserver_port() {
        return inserver_port;
    }

    public void setInserver_port(Integer inserver_port) {
        this.inserver_port = inserver_port;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public ArrayList<Folder> getFolders() {
        return folders;
    }


    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
