package com.example.qmanageapplication.network.responses;

import com.google.android.material.color.utilities.Contrast;

public class AuthResponse {
    private boolean success;
    private String message;
    private UserData user;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public UserData getUser() { return user; }

    public static class UserData {
        private int id;
        private String name;
        private String email;
        private String phone;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
    }
}
