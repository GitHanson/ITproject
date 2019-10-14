package com.example.familyapp2;

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
    private String mKey;

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
        if (familyId != null) {
            setFamily_category_privacy(familyId, category, privacy);
            setFamily_privacy(familyId, privacy);
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

    public String getCategory() {
        return category;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getUserId() {
        return userId;
    }

    public String getFamilyId() {
        return familyId;
    }

    public String getFamily_category_privacy() {
        return family_category_privacy;
    }

    public String getFamily_privacy() {
        return family_privacy;
    }

    public String getUser_format_privacy() {
        return user_format_privacy;
    }

    public void setDescription(String description) {
        if (description.trim().equals("")) {
            this.description = "No Description";
        } else {
            this.description = description;
        }
    }

    public void setCategory(String category) {
        this.category = category;
        if (familyId != null) {
            setFamily_category_privacy(familyId, category, privacy);
        }
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
        setUser_format_privacy(userId, format, privacy);
        if (familyId != null) {
            setFamily_category_privacy(familyId, category, privacy);
            setFamily_privacy(familyId, privacy);
        }
    }

    public void setFamily(String familyId) {
        this.familyId = familyId;
        setFamily_privacy(familyId, privacy);
        setFamily_category_privacy(familyId, category, privacy);
    }

    private void setFamily_category_privacy(String familyId, String category, String privacy) {
        family_category_privacy = familyId + "_" + category + "_" + privacy;
    }

    private void setFamily_privacy(String familyId, String privacy) {
        family_privacy = familyId + "_" + privacy;
    }

    private void setUser_format_privacy(String userId, String format, String privacy) {
        user_format_privacy = userId + "_" + format + "_" + privacy;
    }
    @Exclude
    public String getKey(){
        return mKey;
    }
    @Exclude
    public void setKey(String key){
        mKey = key;
    }

}
