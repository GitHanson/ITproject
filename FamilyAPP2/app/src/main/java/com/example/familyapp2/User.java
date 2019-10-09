package com.example.familyapp2;

public class User {
    private String id, name, email;

    public User() {

    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        //this.email = email;
    }

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
