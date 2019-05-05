package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Folder {

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
}
