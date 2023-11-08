package com.example.scheduling;

//model class for our ListView
public class ListViewUser {
    //Attributes
    private String name, text;
    private int userImage;

    //constructor
    public ListViewUser(String name, String sampleText, int userImage) {
        this.name = name;
        this.text = sampleText;
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
        return text;
    }

    public void setSampleText(String sampleText) {
        this.text = sampleText;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }
}
