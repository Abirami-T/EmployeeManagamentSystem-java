package com.incture.employeeManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.incture.employeeManagementSystem.entities.ErrorResponse;

/**
 * Global exception handler for handling specific and general exceptions across the application.
 * This class uses Spring's @ControllerAdvice to handle exceptions globally.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the custom exception when an employee is not found.
     * Responds with a 404 Not Found status and a detailed error message.
     *
     * @param ex The exception that was thrown.
     * @return A ResponseEntity containing the error response with status 404.
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("EMPLOYEE_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles any general exception that occurs in the application.
     * Responds with a 500 Internal Server Error status and a generic error message.
     *
     * @param ex The exception that was thrown.
     * @return A ResponseEntity containing the error response with status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
