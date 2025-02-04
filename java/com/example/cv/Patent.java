package com.example.cv;

public class Patent {
    private String id;
    private String title;
    private String purpose;
    private String filedOn;
    private String publishedDate;
    private String author1;
    private String author2;

    public Patent() {
        // Default constructor required for calls to DataSnapshot.getValue(Patent.class)
    }

    public Patent(String id, String title, String purpose, String filedOn, String publishedDate, String author1, String author2) {
        this.id = id;
        this.title = title;
        this.purpose = purpose;
        this.filedOn = filedOn;
        this.publishedDate = publishedDate;
        this.author1 = author1;
        this.author2 = author2;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFiledOn() {
        return filedOn;
    }

    public void setFiledOn(String filedOn) {
        this.filedOn = filedOn;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }
}
