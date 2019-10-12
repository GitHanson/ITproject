package com.example.familyapp2;

import com.google.firebase.database.Exclude;

public class Artifacts {

    private String description;
    private String artifactUrl;
    private String thumbnailUrl;
    private String format;
    private String family_category_privacy;
    private String family_privacy;
    private String privacy;
    private String userId;
    private String user_format_privacy;

    //private String mKey;

    //Empty constructor for firebase
    public Artifacts() {

    }

    public Artifacts(String description, String artifactUri, String imageUrl, String format) {
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

    public String getFamily_category_privacy() {
        return family_category_privacy;
    }

    public void setFamily_category_privacy(String family_category_privacy) {
        this.family_category_privacy = family_category_privacy;
    }

    public String getFamily_privacy() {
        return family_privacy;
    }

    public void setFamily_privacy(String family_privacy) {
        this.family_privacy = family_privacy;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_format_privacy() {
        return user_format_privacy;
    }

    public void setUser_format_privacy(String user_format_privacy) {
        this.user_format_privacy = user_format_privacy;
    }

//    @Exclude
//    public String getKey() {
//        return mKey;
//    }
//
//    @Exclude
//    public void setKey(String key) {
//        mKey = key;
//    }
}
