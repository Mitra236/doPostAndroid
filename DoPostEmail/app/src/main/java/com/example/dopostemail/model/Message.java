package com.example.dopostemail.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {
    private int id;
    private String from;
    private ArrayList<String> to;
    private ArrayList<String> cc;
    private ArrayList<String> bcc;
    private String dateTime;
    private String subject;
    private String content;
    private ArrayList<Tag> tag;
    private ArrayList<Attachment> attachments;
    private Folder folder;
    private Account account;
    private boolean messageRead = true;

    public Message(int id, String from, ArrayList<String> to, ArrayList<String> cc, ArrayList<String> bcc, String dateTime, String subject, String content, ArrayList<Tag> tag, ArrayList<Attachment> attachments, Folder folder, Account account, boolean messageRead) {
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

    public Message(String from, ArrayList<String> to, ArrayList<String> cc, ArrayList<String> bcc, String dateTime, String subject, String content, ArrayList<Tag> tag, ArrayList<Attachment> attachments, Folder folder, Account account, boolean messageRead) {
//        this.id = id;
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

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isMessageRead() {
        return messageRead;
    }

    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }
}
