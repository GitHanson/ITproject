package com.example.familyapp2;

import com.google.firebase.database.Exclude;

public class Upload {

    private String name;
    private String imageUrl;
    private String mKey;

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

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
