package com.example.cv;

public class Journal {

    private String id;
    private String articleTitle;
    private String pubName;
    private String monthYear;
    private String volumeNumber;
    private String edition;
    private String isbnNo;
    private String noOfPages;
    private String author1;
    private String author2;
    private String author3;

    // Required empty constructor for Firebase
    public Journal() {
    }

    // Constructor with all fields
    public Journal(String id, String articleTitle, String pubName, String monthYear, String volumeNumber, String edition, String isbnNo, String noOfPages, String author1, String author2, String author3) {
        this.id = id;
        this.articleTitle = articleTitle;
        this.pubName = pubName;
        this.monthYear = monthYear;
        this.volumeNumber = volumeNumber;
        this.edition = edition;
        this.isbnNo = isbnNo;
        this.noOfPages = noOfPages;
        this.author1 = author1;
        this.author2 = author2;
        this.author3 = author3;
    }

    // Getters and Setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(String noOfPages) {
        this.noOfPages = noOfPages;
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

    public String getAuthor3() {
        return author3;
    }

    public void setAuthor3(String author3) {
        this.author3 = author3;
    }
}
