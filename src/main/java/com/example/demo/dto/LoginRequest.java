package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;

    // 1. Default constructor
    public LoginRequest() {
    }

    // 2. All-arguments constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 3. Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // 4. Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 5. toString() method for debugging (password omitted for security)
    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}