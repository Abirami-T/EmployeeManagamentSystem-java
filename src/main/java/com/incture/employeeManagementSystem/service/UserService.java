package com.incture.employeeManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.incture.employeeManagementSystem.dao.UserRepository;
import com.incture.employeeManagementSystem.entities.User;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * Service class responsible for user-related operations such as registering a new user and loading user details for authentication.
 * Implements Spring Security's UserDetailsService interface for loading user data based on username.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Registers a new user by saving the user details to the repository.
     *
     * @param user The user object containing the user's details.
     * @return The saved user object.
     */
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Loads user details by username for authentication, required by Spring Security's UserDetailsService interface.
     * If the user is not found, a UsernameNotFoundException is thrown.
     *
     * @param username The username of the user to be loaded.
     * @return UserDetails containing the user's information and granted authorities.
     * @throws UsernameNotFoundException if the user is not found in the repository.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username, throw exception if not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create granted authority from user role
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        // Return UserDetails object with username, password, and authority
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(authority));
    }
}
