package com.example.dopostemail.model;

import java.io.Serializable;

public class Rule implements Serializable {

    private int id;
    private Condition condition;
    private Operation operation;
    private Folder folder;

    public Rule(int id, Condition condition, Operation operation, Folder folder) {
        this.id = id;
        this.condition = condition;
        this.operation = operation;
        this.folder = folder;
    }

    public Rule(){

    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public int getId() {
        return id;
    }

    public Condition getCondition() {
        return condition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
