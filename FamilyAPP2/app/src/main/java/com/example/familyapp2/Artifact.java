package com.example.familyapp2;

import com.google.firebase.database.Exclude;

public class Artifact {

    private String artifactUrl;
    private String mKey;
    private String family_category_privacy;

    //Empty constructor for firebase
    public Artifact() {

    }

    public Artifact(String artifactUrl, String family_category_privacy) {
        this.artifactUrl = artifactUrl;
        this.family_category_privacy = family_category_privacy;
    }

    public String getfamily_category_privacy() { return family_category_privacy; }


    public String getImageUrl() {
        return artifactUrl;
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
