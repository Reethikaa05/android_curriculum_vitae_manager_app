package com.example.cv;

public class PhDDetails {
    private String id;
    private String scholarName;
    private String regNo;
    private String regDate;
    private String submissionDate;
    private String vivaDate;
    private String degreeType;
    private String modeOfDegree;
    private String status;

    // Default constructor required for calls to DataSnapshot.getValue(PhDDetails.class)
    public PhDDetails() {
    }

    // Constructor with parameters
    public PhDDetails(String id, String scholarName, String regNo, String regDate,
                      String submissionDate, String vivaDate, String degreeType,
                      String modeOfDegree, String status) {
        this.id = id;
        this.scholarName = scholarName;
        this.regNo = regNo;
        this.regDate = regDate;
        this.submissionDate = submissionDate;
        this.vivaDate = vivaDate;
        this.degreeType = degreeType;
        this.modeOfDegree = modeOfDegree;
        this.status = status;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScholarName() {
        return scholarName;
    }

    public void setScholarName(String scholarName) {
        this.scholarName = scholarName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getVivaDate() {
        return vivaDate;
    }

    public void setVivaDate(String vivaDate) {
        this.vivaDate = vivaDate;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getModeOfDegree() {
        return modeOfDegree;
    }

    public void setModeOfDegree(String modeOfDegree) {
        this.modeOfDegree = modeOfDegree;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
