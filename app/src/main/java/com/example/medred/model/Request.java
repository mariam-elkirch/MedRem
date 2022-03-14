package com.example.medred.model;

public class Request {
    private String senderEmail;
    private String senderName;

    public Request(String senderEmail, String senderName) {
        this.senderEmail = senderEmail;
        this.senderName = senderName;
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
}

