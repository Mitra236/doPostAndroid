package com.example.dopostemail.model;

import java.util.ArrayList;

public class Dummy {

    private Folder folder;
    private Folder folder1;
    private Message message;
    private Contact contact;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    public Dummy() {

        folder1 = new Folder();
        folder1.setId(1);
        folder1.setName("Drafts");
        folder1.setMessages(new ArrayList<Message>());
        folders.add(folder1);

        folder = new Folder();
        folder.setId(2);
        folder.setName("Promotions");
        folder.setMessages(new ArrayList<Message>());
        folders.add(folder);

        folder = new Folder();
        folder.setId(3);
        folder.setName("Trash");
        folder.setMessages(new ArrayList<Message>());
        folders.add(folder);

        Contact contact = new Contact();
        contact.setId(1);
        contact.setFirstName("Emily");
        contact.setLastName("Peric");
        contact.setEmail("SFSFSD@gmail.com");
        contacts.add(contact);

        Contact contact1 = new Contact();
        contact1.setId(1);
        contact1.setFirstName("Mark");
        contact1.setLastName("Peric");
        contact1.setEmail("asdasdasasda@gmail.com");
        contacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setId(2);
        contact2.setFirstName("John");
        contact2.setLastName("Peric");
        contact2.setEmail("asdasdasdsa@gmail.com");
        contacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setId(3);
        contact3.setFirstName("Jovan");
        contact3.setLastName("Peric");
        contact3.setEmail("asdasdasasda@gmail.com");
        contacts.add(contact3);

        Contact contact4 = new Contact();
        contact4.setId(4);
        contact4.setFirstName("Anna");
        contact4.setLastName("Peric");
        contact4.setEmail("asdasdasdsa@gmail.com");
        contacts.add(contact4);

        Contact contact5 = new Contact();
        contact5.setId(4);
        contact5.setFirstName("Mima");
        contact5.setLastName("Peric");
        contact5.setEmail("asdasdasdsa@gmail.com");
        contacts.add(contact5);

        message = new Message();
        message.setId(1);
        message.setFrom(contact2);
        message.setDateTime("2019-15-03 15:25");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder.getMessages().add(message);
        messages.add(message);

        message = new Message();
        message.setId(2);
        message.setFrom(contact1);
        message.setDateTime("2019-15-06 14:00");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder.getMessages().add(message);
        messages.add(message);

        message = new Message();
        message.setId(3);
        message.setFrom(contact);
        message.setDateTime("2019-21-03 12:25");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder.getMessages().add(message);
        messages.add(message);

        message = new Message();
        message.setId(4);
        message.setFrom(contact3);
        message.setDateTime("2019-11-02 08:00");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder1.getMessages().add(message);
        messages.add(message);

        message = new Message();
        message.setId(5);
        message.setFrom(contact4);
        message.setDateTime("2019-11-07 12:10");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder1.getMessages().add(message);
        messages.add(message);

        message = new Message();
        message.setId(5);
        message.setFrom(contact5);
        message.setDateTime("2019-11-07 12:10");
        message.setSubject("Hello");
        message.setContent("Message1");
        folder1.getMessages().add(message);
        messages.add(message);



    }

    public Folder getFolder() {
        return folder;
    }

    public Message getMessage() {
        return message;
    }

    public Contact getContact() {
        return contact;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
