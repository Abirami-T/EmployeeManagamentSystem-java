package com.incture.employeeManagementSystem.dao;

import com.incture.employeeManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the User entity.
 * This interface extends JpaRepository to provide built-in methods for data access.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     * 
     * @param username The username of the user to find.
     * @return An Optional containing the User if found, or an empty Optional if no user is found with the specified username.
     */
    Optional<User> findByUsername(String username);
}