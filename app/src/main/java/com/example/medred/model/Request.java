package com.example.medred.model;

public class Request {
    private String senderEmail;
    private String senderName;
    private String senderId;

    public Request(String senderEmail, String senderName, String senderId) {
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.senderId = senderId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}

