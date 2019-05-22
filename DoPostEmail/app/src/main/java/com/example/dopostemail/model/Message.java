package com.example.dopostemail.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {
    private int id;
    private Contact from;
    private ArrayList<Contact> to;
    private ArrayList<Contact> cc;
    private ArrayList<Contact> bcc;
    private Date dateTime;
    private String subject;
    private String content;
    private ArrayList<Tag> tag;
    private ArrayList<Attachment> attachments;
    private Folder folder;
    private Account account;
    private boolean messageRead = true;

    public Message(int id, String subject, String content) {
        this.id = id;
        this.subject = subject;
        this.content = content;
    }

    public Message(int id, Contact from, ArrayList<Contact> to, ArrayList<Contact> cc, ArrayList<Contact> bcc, Date dateTime, String subject, String content, ArrayList<Tag> tag,
                   ArrayList<Attachment> attachments, Folder folder, Account account, boolean messageRead) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;
        this.tag = tag;
        this.attachments = attachments;
        this.folder = folder;
        this.account = account;
        this.messageRead = messageRead;
    }

    public Message(Contact from, String subject) {
        this.from = from;
        this.subject = subject;

    }


    public Message() {
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public Folder getFolder() {
        return folder;
    }

    public Account getAccount() {
        return account;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact getFrom() {
        return from;
    }

    public void setFrom(Contact from) {
        this.from = from;
    }

    public ArrayList<Contact> getTo() {
        return to;
    }

    public void setTo(ArrayList<Contact> to) {
        this.to = to;
    }

    public ArrayList<Contact> getCc() {
        return cc;
    }

    public void setCc(ArrayList<Contact> cc) {
        this.cc = cc;
    }

    public ArrayList<Contact> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<Contact> bcc) {
        this.bcc = bcc;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMessageRead() {return messageRead;}

    public void setMessageRead(boolean messageRead){this.messageRead = messageRead;}

}
