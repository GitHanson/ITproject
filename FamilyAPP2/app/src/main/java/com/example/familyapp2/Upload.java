package com.example.familyapp2;

import com.google.firebase.database.Exclude;

public class Upload {

    private String description;
    private String artifactUrl;
    private String thumbnailUrl;
    private String format;

    //Empty constructor for firebase
    public Upload() {

    }

    public Upload(String description, String artifactUri, String imageUrl, String format) {
        setDescription(description);
        this.artifactUrl = artifactUri;
        this.thumbnailUrl = imageUrl;
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public String getArtifactUrl() {
        return artifactUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setDescription(String description) {
        if (description.trim().equals("")) {
            this.description = "No Description";
        }
        else {
            this.description = description;
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
