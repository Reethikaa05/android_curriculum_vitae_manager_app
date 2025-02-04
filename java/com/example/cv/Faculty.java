package com.example.cv;


public class Faculty {

    private String facultyId;
    private String name;
    private String designation;
    private String department;
    private String age;
    private String gender;
    private String contact;
    private String email;
    private String imageUrl;  // Add this for storing the image URL if needed

    public Faculty() {
        // Default constructor required for Firebase
    }

    public Faculty(String facultyId, String name, String designation, String department, String age,
                   String gender, String contact, String email, String imageUrl) {
        this.facultyId = facultyId;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
