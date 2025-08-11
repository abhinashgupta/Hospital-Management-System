package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.User;
import com.example.demo.repository.DoctorRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return ResponseEntity.ok(doctor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setName(doctorDetails.getName());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setContactNumber(doctorDetails.getContactNumber());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
            
        doctorRepository.delete(doctor);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/me")
    public ResponseEntity<Doctor> getCurrentDoctorProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Doctor doctorProfile = doctorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Doctor profile not found for current user"));
        
        return ResponseEntity.ok(doctorProfile);
    }

    @PostMapping
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    // You can add GetByID, Update, and Delete endpoints here similar to the PatientController
}