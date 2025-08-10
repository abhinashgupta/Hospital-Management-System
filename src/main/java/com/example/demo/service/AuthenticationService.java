package com.example.demo.service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthenticationResponse signup(SignUpRequest request) {
    	 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
    	        throw new IllegalArgumentException("Email address is already in use.");
    	    }
    	 
    	 if (request.getRole() == Role.DOCTOR) {
    	        if (request.getSpecialization() == null || request.getSpecialization().isBlank()) {
    	            throw new IllegalArgumentException("Specialization is required when registering as a Doctor.");
    	        }
    	    }
    	 
    	// Create the User object in memory (don't save yet)
    	    User user = new User();
    	    user.setEmail(request.getEmail());
    	    user.setRole(request.getRole());
    	    user.setPassword(passwordEncoder.encode(request.getPassword()));
    	    
    	    // Create and link the profile based on role
    	    if (request.getRole() == Role.DOCTOR) {
    	        if (request.getSpecialization() == null || request.getSpecialization().isBlank()) {
    	            throw new IllegalArgumentException("Specialization is required when registering as a Doctor.");
    	        }
    	        Doctor doctor = new Doctor();
    	        doctor.setName(request.getName());
    	        doctor.setSpecialization(request.getSpecialization());
    	        doctor.setContactNumber(request.getContactNumber()); 
    	        
    	        // Set the bidirectional relationship
    	        doctor.setUser(user);
    	        user.setDoctor(doctor);

    	    } else if (request.getRole() == Role.PATIENT) {
    	        Patient patient = new Patient();
    	        patient.setName(request.getName());
    	        patient.setAge(request.getAge());
    	        patient.setGender(request.getGender());
    	        patient.setDiagnosis(request.getDiagnosis());
    	        
    	        // Set the bidirectional relationship
    	        patient.setUser(user);
    	        user.setPatient(patient);
    	    }

    	    // NOW, save the User. This will also save the linked profile due to CascadeType.ALL
    	    userRepository.save(user);

    	    // Generate and return the token
    	    String jwt = jwtService.generateToken(user);
    	    return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
