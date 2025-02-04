package com.example.cv;

public class Book {
    private String bookId;
    private String title;
    private String publisher;
    private String monthYear;
    private String edition;
    private String isbnNo;
    private String noOfPages;
    private String author1;
    private String author2;
    private String author3;

    // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    public Book() {
        // Default constructor
    }

    // Parameterized constructor
    public Book(String bookId, String title, String publisher, String monthYear, String edition, String isbnNo, String noOfPages, String author1, String author2, String author3) {
        this.bookId = bookId;
        this.title = title;
        this.publisher = publisher;
        this.monthYear = monthYear;
        this.edition = edition;
        this.isbnNo = isbnNo;
        this.noOfPages = noOfPages;
        this.author1 = author1;
        this.author2 = author2;
        this.author3 = author3;
    }

    // Getters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getPublisher() { return publisher; }
    public String getMonthYear() { return monthYear; }
    public String getEdition() { return edition; }
    public String getIsbnNo() { return isbnNo; }
    public String getNoOfPages() { return noOfPages; }
    public String getAuthor1() { return author1; }
    public String getAuthor2() { return author2; }
    public String getAuthor3() { return author3; }

    // Setters
    public void setBookId(String bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public void setEdition(String edition) { this.edition = edition; }
    public void setIsbnNo(String isbnNo) { this.isbnNo = isbnNo; }
    public void setNoOfPages(String noOfPages) { this.noOfPages = noOfPages; }
    public void setAuthor1(String author1) { this.author1 = author1; }
    public void setAuthor2(String author2) { this.author2 = author2; }
    public void setAuthor3(String author3) { this.author3 = author3; }
}
