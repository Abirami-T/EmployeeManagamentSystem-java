package com.incture.employeeManagementSystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.incture.employeeManagementSystem.controller.EmployeeController;
import com.incture.employeeManagementSystem.entities.Employee;
import com.incture.employeeManagementSystem.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee emp1;
    private Employee emp2;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emp1 = new Employee(1L, "John Doe", "john.doe@example.com", "1234567890", 50000.0, "Developer", "IT", "Software Engineer");
        emp2 = new Employee(2L, "Jane Smith", "jane.smith@example.com", "0987654321", 60000.0, "Manager", "Sales", "Sales Manager");
        employees = Arrays.asList(emp1, emp2);
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.createEmployee(emp1)).thenReturn(emp1);

        String result = employeeController.createEmployee(emp1);

        assertEquals("Employee Details Stored successfully!", result);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<Employee> result = employeeController.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(emp1);

        Employee result = employeeController.getEmployeeById(1L);

        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateEmployee() {
        Employee updatedEmp = new Employee(1L, "John Smith", "john.smith@example.com", "1234567890", 52000.0, "Senior Developer", "IT", "Software Engineer");
        when(employeeService.updateEmployee(1L, updatedEmp)).thenReturn(updatedEmp);

        String result = employeeController.updateEmployee(1L, updatedEmp);

        assertEquals("Employee details updated successfully!", result);
    }

    @Test
    void testDeleteEmployee() {
        // Use doNothing() since deleteEmployee() is void
        doNothing().when(employeeService).deleteEmployee(1L);

        String result = employeeController.deleteEmployee(1L);

        assertEquals("Employee deleted successfully!", result);
    }
}
