package com.example.cv;

public class BookChapter {
    private String id;
    private String title;
    private String publisher;
    private String monthYear;
    private String chapterNumber;
    private String edition;
    private String isbn;
    private String pages;
    private String author1;
    private String author2;
    private String author3;

    public BookChapter() {
        // Default constructor required for calls to DataSnapshot.getValue(BookChapter.class)
    }

    public BookChapter(String id, String title, String publisher, String monthYear, String chapterNumber,
                       String edition, String isbn, String pages, String author1, String author2, String author3) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.monthYear = monthYear;
        this.chapterNumber = chapterNumber;
        this.edition = edition;
        this.isbn = isbn;
        this.pages = pages;
        this.author1 = author1;
        this.author2 = author2;
        this.author3 = author3;
    }

    // Getters and Setters
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
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
