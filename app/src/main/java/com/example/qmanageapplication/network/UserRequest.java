package com.example.qmanageapplication.network;

public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String phone;

    // Login constructor
    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Register constructor
    public UserRequest(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
