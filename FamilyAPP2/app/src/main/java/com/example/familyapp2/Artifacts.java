package com.example.familyapp2;

import com.google.firebase.database.Exclude;

public class Artifacts {

    private String description;
    private String artifactUrl;
    private String thumbnailUrl;
    private String format;
    private String category;
    private String privacy;
    private String userId;
    private String familyId;
    private String family_category_privacy;
    private String family_privacy;
    private String user_format_privacy;

    //private String mKey;

    //Empty constructor for firebase
    public Artifacts() {

    }

    public Artifacts(String description, String artifactUri, String imageUrl, String format, String category, String privacy, String userId, String familyId) {
        setDescription(description);
        this.artifactUrl = artifactUri;
        this.thumbnailUrl = imageUrl;
        this.format = format;
        this.category = category;
        this.privacy = privacy;
        this.userId = userId;
        this.familyId = familyId;
        setUser_format_privacy(userId, format, privacy);
        if(!familyId.equals("None")) {
            setFamily_category_privacy(familyId, category, privacy);
            setFamily_privacy(familyId, privacy);
        }
        else {
            family_category_privacy = "None";
            family_privacy = "None";
        }
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

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
        setUser_format_privacy(userId, format, privacy);
        if(!familyId.equals("None")) {
            setFamily_category_privacy(familyId, category, privacy);
            setFamily_privacy(familyId, privacy);
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
        setFamily_privacy(familyId, privacy);
        setFamily_category_privacy(familyId, category, privacy);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        if(!familyId.equals("None")) {
            setFamily_category_privacy(familyId, category, privacy);
        }
    }

    public String getFamily_category_privacy() {
        return family_category_privacy;
    }

    private void setFamily_category_privacy(String familyId, String category, String privacy) {
        family_category_privacy = familyId + "_" + category + "_" + privacy;
    }

    public String getFamily_privacy() {
        return family_privacy;
    }

    private void setFamily_privacy(String familyId, String privacy) {
        family_privacy = familyId + "_" + privacy;
    }

    public String getUser_format_privacy() {
        return user_format_privacy;
    }

    private void setUser_format_privacy(String userId, String format, String privacy) {
        user_format_privacy = userId + "_" + format + "_" + privacy;
    }
}
