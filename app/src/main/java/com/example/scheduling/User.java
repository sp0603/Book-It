package com.example.scheduling;

//model class for our ListView
public class User {
    //Attributes
    private String name, email;
    private int userImage;

    //constructor
    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }
    public User(String name, String sampleText, int userImage) {
        this.name = name;
        this.email = sampleText;
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
        return email;
    }

    public void setSampleText(String sampleText) {
        this.email = sampleText;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }
}
