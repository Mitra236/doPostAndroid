package com.example.dopostemail.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Message message;
    private User user;


    public Tag(Long id, String name, Message message, User user) {
        super();
        this.id = id;
        this.name = name;
        this.message = message;
        this.user = user;
    }




    public Message getMessage() {
        return message;
    }


    public void setMessage(Message message) {
        this.message = message;
    }


    public Tag() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }



//    public ArrayList<MessageDTO> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(ArrayList<MessageDTO> messages) {
//        this.messages = messages;
//    }
}
