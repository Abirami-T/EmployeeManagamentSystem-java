package com.incture.employeeManagementSystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.employeeManagementSystem.dao.RoleRepository;
import com.incture.employeeManagementSystem.dao.UserRepository;
import com.incture.employeeManagementSystem.entities.Role;
import com.incture.employeeManagementSystem.entities.User;
import com.incture.employeeManagementSystem.entities.UserRegistrationRequest;
import com.incture.employeeManagementSystem.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint to register a new user with a role.
     * 
     * @param userRegistrationRequest The user registration request with username, password, and role
     * @return The registered user if successful, otherwise an error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {

        // Check if username already exists
        if (userRepository.findByUsername(userRegistrationRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        }

        // Find the role from the Role table
        Optional<Role> roleOptional = roleRepository.findByName(userRegistrationRequest.getRole());
        if (!roleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found.");
        }

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());

        // Create the user and assign the found role
        User newUser = new User();
        newUser.setUsername(userRegistrationRequest.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole(roleOptional.get());  // Set the role from the Role table

        // Save the new user to the database
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);  // Return the created user
    }
}
