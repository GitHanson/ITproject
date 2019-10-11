package com.example.familyapp2;

public class Icon {
    private String IconUrl;
    // empty constructor
    public Icon(){}
    // constructor
    public Icon(String iconUrl){
        IconUrl = iconUrl;

    }

    public String getIconUrl(){
        return IconUrl;
    }
    public void setIconUrl(String iconUrl){
        IconUrl =  iconUrl;
    }

}
