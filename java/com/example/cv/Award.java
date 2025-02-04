package com.example.cv;

public class Award {
    private String id;
    private String awardName;
    private String sponsoringAgency;
    private String purpose;
    private String month;
    private String year;
    private String type;

    // Default constructor required for calls to DataSnapshot.getValue(Award.class)
    public Award() {
    }

    // Parameterized constructor
    public Award(String id, String awardName, String sponsoringAgency, String purpose, String month, String year, String type) {
        this.id = id;
        this.awardName = awardName;
        this.sponsoringAgency = sponsoringAgency;
        this.purpose = purpose;
        this.month = month;
        this.year = year;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getSponsoringAgency() {
        return sponsoringAgency;
    }

    public void setSponsoringAgency(String sponsoringAgency) {
        this.sponsoringAgency = sponsoringAgency;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
