package com.incture.employeeManagementSystem.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.employeeManagementSystem.entities.Role;

/**
 * Repository interface for performing CRUD operations on the Role entity.
 * This interface extends JpaRepository to provide built-in methods for data access.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     * 
     * @param name The name of the role to find.
     * @return An Optional containing the Role if found, or an empty Optional if no role is found with the specified name.
     */
    Optional<Role> findByName(String name);  // Find role by its name
}
