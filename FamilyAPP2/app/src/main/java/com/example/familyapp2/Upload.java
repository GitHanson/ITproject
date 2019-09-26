package com.example.familyapp2;

public class Upload {

    private String name;
    private String imageUrl;

    //Empty constructor for firebase
    public Upload() {

    }

    public Upload(String name, String imageUrl) {
        setName(name);
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        if (name.trim().equals("")) {
            this.name = "No Name";
        }
        else {
            this.name = name;
        }
    }
}
