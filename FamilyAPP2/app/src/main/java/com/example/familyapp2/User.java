package com.example.familyapp2;

public class User {
    public String name, email;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
