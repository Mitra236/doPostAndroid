package com.example.dopostemail.model;

import java.io.Serializable;

public class Attachment implements Serializable {

    private Long id;
    private String data;
    private String type;
    private String name;
    private Message message;

    public Attachment(Long id, String data, String type, String name, Message message) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.name = name;
        this.message = message;
    }

    public Attachment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
