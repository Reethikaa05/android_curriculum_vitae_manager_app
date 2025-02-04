package com.example.cv;

public class Consultancy {
    private String id;
    private String projectTitle;
    private String typeOfProject;
    private String sponsoringAgency;
    private String amount;
    private String startDate;
    private String endDate;
    private String yearOfCompletion;
    private String status;

    // Default constructor required for calls to DataSnapshot.getValue(Consultancy.class)
    public Consultancy() {
    }

    // Parameterized constructor
    public Consultancy(String id, String projectTitle, String typeOfProject, String sponsoringAgency,
                       String amount, String startDate, String endDate, String yearOfCompletion, String status) {
        this.id = id;
        this.projectTitle = projectTitle;
        this.typeOfProject = typeOfProject;
        this.sponsoringAgency = sponsoringAgency;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearOfCompletion = yearOfCompletion;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getTypeOfProject() {
        return typeOfProject;
    }

    public void setTypeOfProject(String typeOfProject) {
        this.typeOfProject = typeOfProject;
    }

    public String getSponsoringAgency() {
        return sponsoringAgency;
    }

    public void setSponsoringAgency(String sponsoringAgency) {
        this.sponsoringAgency = sponsoringAgency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getYearOfCompletion() {
        return yearOfCompletion;
    }

    public void setYearOfCompletion(String yearOfCompletion) {
        this.yearOfCompletion = yearOfCompletion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
