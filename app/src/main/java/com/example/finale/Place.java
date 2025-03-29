package com.example.finale;

public class Place {
    private String name;
    private String description;
    private int imageResourceId;
    private String phoneNumber;
    private String email;

    public Place(String name, String description, int imageResourceId, String phoneNumber, String email) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
} 