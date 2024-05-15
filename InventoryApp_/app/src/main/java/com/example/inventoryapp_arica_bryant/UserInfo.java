package com.example.inventoryapp_arica_bryant;

public class UserInfo {
    // Private variables
    private String username;
    private String password;

    private int userId;

    // Constructor
    public UserInfo(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
