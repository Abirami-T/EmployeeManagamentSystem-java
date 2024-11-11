package com.incture.employeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.employeeManagementSystem.entities.Employee;
import com.incture.employeeManagementSystem.service.EmployeeService;

@RestController
@RequestMapping("/profile")
public class EmployeeProfileController {

	@Autowired
    private EmployeeService employeeService;

	/**
     * Endpoint to retrieve a specific employee by ID.
     * @param id The ID of the employee
     * @return Employee object with the given ID
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

}
