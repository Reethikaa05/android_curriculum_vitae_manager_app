package com.example.cv;

public class User {
    private String username;
    private String email;

    // Default constructor required for Firestore
    public User() {}

    // Constructor to initialize User object
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getter for name
    public String getName() {
        return username;
    }

    // Setter for name
    public void setName(String name) {
        this.username = name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }
}
