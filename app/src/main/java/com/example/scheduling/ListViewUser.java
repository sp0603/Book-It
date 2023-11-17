package com.example.scheduling;

//model class for our ListView
public class ListViewUser {
    //Attributes
    private String name;
    private String profilePictureUrl;

    //constructor
    public ListViewUser(String name, String profilePictureUrl) {
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
    }

    //getters and setters

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
