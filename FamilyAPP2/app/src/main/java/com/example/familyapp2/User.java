package com.example.familyapp2;

public class User {
    public String name, email, profileUrl, family;

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
