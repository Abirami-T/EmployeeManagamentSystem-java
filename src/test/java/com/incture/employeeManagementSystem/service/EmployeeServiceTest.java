package com.incture.employeeManagementSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.incture.employeeManagementSystem.dao.EmployeeRepository;
import com.incture.employeeManagementSystem.entities.Employee;



class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> employees;
    private Employee emp1, emp2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        emp1 = new Employee(1L, "John Doe", "john.doe@example.com", "1234567890", 50000.0, "Developer", "IT", "Software Engineer");
        emp2 = new Employee(2L, "Jane Doe", "jane.doe@example.com", "0987654321", 55000.0, "Manager", "HR", "HR Manager");

        employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
    }
    
    @Test
    void testAddEmployee() {
        when(employeeRepository.save(emp1)).thenReturn(emp1);

        Employee result = employeeService.createEmployee(emp1);
        assertEquals("John Doe", result.getName());
        assertEquals(50000, result.getSalary());
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testGetEmployeeById() {
        // Arrange: Mock the repository to return emp1 when finding by ID 1
        when(employeeRepository.existsById(1L)).thenReturn(true); // Simulate that the employee exists
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1)); // Return employee emp1

        // Act: Call the service method to get the employee by ID
        Employee result = employeeService.getEmployeeById(1L);

        // Assert: Verify the result is not null and the expected employee data
        assertNotNull(result);  // Ensure the result is not null
        assertEquals("John Doe", result.getName());  // Verify the name of the employee
        assertEquals(50000, result.getSalary());  // Verify the salary of the employee
    }

   

    @Test
    void testUpdateEmployee() {
        // Create updated employee details
        Employee updatedEmp = new Employee(1L, "John Smith", "john.smith@example.com", "1234567890", 52000.0, "Senior Developer", "IT", "Software Engineer");

        // Mock the repository to return the original employee for ID 1
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));

        // Mock the repository to return the updated employee
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmp);  // Use any() to match the argument passed to save

        // Call the service method
        Employee result = employeeService.updateEmployee(1L, updatedEmp);

        // Assertions to verify the updated employee
        assertNotNull(result);
        assertEquals("John Smith", result.getName());  // Verify name is updated
        assertEquals(52000.0, result.getSalary());  // Verify salary is updated

        // If employee does not exist, ensure exception is thrown
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
        assertNull(employeeService.updateEmployee(2L, updatedEmp)); // should return null
    }


    @Test
    void testDeleteEmployee() {
        // Mock the repository to simulate an existing employee
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        // Call the service method
        employeeService.deleteEmployee(1L);

        // Verify that the deleteById method was called exactly once
        verify(employeeRepository, times(1)).deleteById(1L);

        // If employee does not exist, ensure exception is thrown
        when(employeeRepository.existsById(2L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployee(2L));
    }

}
