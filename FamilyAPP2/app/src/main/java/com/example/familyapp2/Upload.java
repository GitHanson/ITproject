package com.example.familyapp2;

public class Upload {

    private String description;
    private String imageUrl;

    //Empty constructor for firebase
    public Upload() {

    }

    public Upload(String name, String imageUrl) {
        setName(name);
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String description) {
        if (description.trim().equals("")) {
            this.description = "Empty";
        }
        else {
            this.description = description;
        }
    }
}
