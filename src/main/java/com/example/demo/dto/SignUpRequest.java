package com.example.demo.dto;

import com.example.demo.model.Role;

public class SignUpRequest {
    private String name; // For Patient or Doctor's name
    private String email;
    private String password;
    private Role role;
    private String specialization; // For doctors
    private String contactNumber;

    // Fields for patient details
    private Integer age;
    private String gender;
    private String diagnosis;

    // 1. Default constructor
    public SignUpRequest() {
    }

    // 2. Updated All-arguments constructor
    public SignUpRequest(String name, String email, String password, Role role, String specialization, Integer age, String gender, String diagnosis) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.specialization = specialization;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
    }

    // 3. Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    // 4. Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }


    // 5. Updated toString() method for debugging
    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", specialization='" + specialization + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}