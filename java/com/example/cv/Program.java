package com.example.cv;

public class Program {
    private String programId;
    private String title;
    private String organizedBy;
    private String purpose;
    private String startDate;
    private String endDate;
    private String place;
    private String programType;

    public Program() {
        // Default constructor required for calls to DataSnapshot.getValue(Program.class)
    }

    public Program(String programId, String title, String organizedBy, String purpose, String startDate, String endDate, String place, String programType) {
        this.programId = programId;
        this.title = title;
        this.organizedBy = organizedBy;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.programType = programType;
    }

    // Getters
    public String getProgramId() {
        return programId;
    }

    public String getTitle() {
        return title;
    }

    public String getOrganizedBy() {
        return organizedBy;
    }

    public String getPurpose() {
        return purpose;
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

    public String getProgramType() {
        return programType;
    }

    // Setters
    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrganizedBy(String organizedBy) {
        this.organizedBy = organizedBy;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public void setProgramType(String programType) {
        this.programType = programType;
    }
}
