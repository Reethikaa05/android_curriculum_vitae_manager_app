package com.example.cv;

public class Education {
    private String degreeName;
    private String specialisation;
    private String collegeName;
    private String monthYearOfPassing;
    private String degreeType;

    // Default constructor required for Firebase Realtime Database
    public Education() {
    }

    // Parameterized constructor
    public Education(String degreeName, String specialisation, String collegeName, String monthYearOfPassing, String degreeType) {
        this.degreeName = degreeName;
        this.specialisation = specialisation;
        this.collegeName = collegeName;
        this.monthYearOfPassing = monthYearOfPassing;
        this.degreeType = degreeType;
    }

    // Getters and Setters
    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getMonthYearOfPassing() {
        return monthYearOfPassing;
    }

    public void setMonthYearOfPassing(String monthYearOfPassing) {
        this.monthYearOfPassing = monthYearOfPassing;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }


}
