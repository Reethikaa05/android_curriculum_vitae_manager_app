package com.example.cv;

/** @noinspection ALL*/
public class ResearchInterest {

    private String title;

    // Default constructor (required for Firebase)
    public ResearchInterest() {}

    // Parameterized constructor
    public ResearchInterest(String title) {
        this.title = title;
    }

    // Getter for Research Title
    public String getTitle() {
        return title;
    }

    // Setter for Research Title
    public void setTitle(String title) {
        this.title = title;
    }
}

