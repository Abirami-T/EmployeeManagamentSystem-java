package com.incture.employeeManagementSystem.exception;

/**
 * Custom exception class to handle cases where an employee is not found.
 * This exception extends RuntimeException and is thrown when an employee
 * cannot be found in the system.
 */
public class EmployeeNotFoundException extends RuntimeException {

    /**
     * Constructor to create an instance of EmployeeNotFoundException with a custom message.
     *
     * @param message The detail message that describes the exception.
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
