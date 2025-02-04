package com.example.cv;

/** @noinspection ALL*/
public class Experience {
    private String jobTitle;
    private String yearsOfExperience;
    private String description;
    private String experienceType;

    // Default constructor required for calls to DataSnapshot.getValue(Experience.class)
    public Experience() {
    }

    // Getter and Setter methods
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(String experienceType) {
        this.experienceType = experienceType;
    }
}
