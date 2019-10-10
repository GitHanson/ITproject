package com.example.familyapp2;

public class User {
    private String id, name, email;
    public String profileUrl, family;

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

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
