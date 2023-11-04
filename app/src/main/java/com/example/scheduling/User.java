package com.example.scheduling;

//model class for our ListView
public class User {
    //Attributes
    private String name, sampleText;
    private int userImage;

    //constructor
    public User(String name, String sampleText, int userImage) {
        this.name = name;
        this.sampleText = sampleText;
        this.userImage = userImage;
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }
}
