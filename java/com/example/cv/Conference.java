package com.example.cv;

public class Conference {
    private String conferenceId;
    private String title;
    private String conferenceName;
    private String publisherName;
    private String monthYear;
    private String renewal;
    private String noOfPages;

    // Default constructor required for Firebase
    public Conference() {
    }

    public Conference(String conferenceId, String title, String conferenceName, String publisherName, String monthYear, String renewal, String noOfPages) {
        this.conferenceId = conferenceId;
        this.title = title;
        this.conferenceName = conferenceName;
        this.publisherName = publisherName;
        this.monthYear = monthYear;
        this.renewal = renewal;
        this.noOfPages = noOfPages;
    }

    // Getters and setters
    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getRenewal() {
        return renewal;
    }

    public void setRenewal(String renewal) {
        this.renewal = renewal;
    }

    public String getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(String noOfPages) {
        this.noOfPages = noOfPages;
    }
}
