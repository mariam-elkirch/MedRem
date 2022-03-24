package com.example.medred.model;

public class User {

    String email;
    String name;
    String password;
    String uid;

    public User() {
    }

    public User(String email, String name,String password) {
        this.email = email;
        this.name = name;
        this.password=password;
    }

    public User(String email, String name,String password, String uid) {
        this.email = email;
        this.name = name;
        this.password=password;
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
