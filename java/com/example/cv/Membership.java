package com.example.cv;

public class Membership {
    private String memberType;
    private String year;
    private String description;

    public Membership() {
        // Required for Firebase
    }

    // Constructor
    public Membership(String memberType, String year, String description) {
        this.memberType = memberType;
        this.year = year;
        this.description = description;
    }

    // Getters
    public String getMemberType() {
        return memberType;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
