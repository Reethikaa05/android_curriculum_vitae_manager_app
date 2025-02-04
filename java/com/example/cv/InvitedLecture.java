package com.example.cv;

public class InvitedLecture {
    private String id;
    private String title;
    private String purpose;
    private String organizedBy;
    private String startDate;
    private String endDate;
    private String place;

    // Default constructor required for calls to DataSnapshot.getValue(InvitedLecture.class)
    public InvitedLecture() {
    }

    // Constructor with parameters
    public InvitedLecture(String id, String title, String purpose, String organizedBy, String startDate, String endDate, String place) {
        this.id = id;
        this.title = title;
        this.purpose = purpose;
        this.organizedBy = organizedBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getOrganizedBy() {
        return organizedBy;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPlace() {
        return place;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setOrganizedBy(String organizedBy) {
        this.organizedBy = organizedBy;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
