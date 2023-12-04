package com.example.scheduling;

public class ListViewEvent {
    private String eventName;
    private String startTime;
    private String endTime;
    private String notes;
    private String eventDate;
    private String eventID;

    public ListViewEvent(String eventName, String startTime, String endTime, String notes, String eventDate, String eventID) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.eventDate = eventDate;
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}