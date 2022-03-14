package com.example.medred.model;

public class HealthTaker {

    private String name;
    private String email;
    private String imgURL;

    public HealthTaker(String name, String email, String imgURL) {
        this.name = name;
        this.email = email;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
