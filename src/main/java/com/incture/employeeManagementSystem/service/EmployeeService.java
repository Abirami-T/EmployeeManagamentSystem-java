package com.incture.employeeManagementSystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.employeeManagementSystem.dao.EmployeeRepository;
import com.incture.employeeManagementSystem.entities.Employee;
import com.incture.employeeManagementSystem.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class that provides business logic for managing employee operations.
 * This class interacts with the EmployeeRepository to perform CRUD operations and other employee-related functionalities.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a new employee and saves it to the database.
     *
     * @param employee The employee object to be created.
     * @return The created employee object with an assigned ID.
     */
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Creating new employee: {}", employee.getName());
        Employee savedEmployee = employeeRepository.save(employee);
        LOGGER.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return savedEmployee;
    }

    /**
     * Fetches all employees from the database.
     *
     * @return A list of all employees.
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    /**
     * Fetches an employee by its ID.
     *
     * @param id The ID of the employee to be fetched.
     * @return The employee object if found, or null if not found.
     * @throws NoSuchElementException if no employee is found with the given ID.
     */
    public Employee getEmployeeById(Long id) {
        if (!this.employeeRepository.existsById(id)) {
            LOGGER.error("Employee not found with id: {}", id);
            throw new NoSuchElementException("Employee not found with id: " + id);
        }
        LOGGER.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing employee with new details.
     *
     * @param id The ID of the employee to be updated.
     * @param updatedEmployee The employee object containing the updated details.
     * @return The updated employee object if the update is successful, or null if not found.
     * @throws EmployeeNotFoundException if the employee with the given ID is not found.
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        LOGGER.info("Updating employee with ID: {}", id);
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setName(updatedEmployee.getName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setRole(updatedEmployee.getRole());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setJobTitle(updatedEmployee.getJobTitle());
            LOGGER.info("Employee updated successfully with ID: {}", id);
            return employeeRepository.save(employee);
        } else {
            LOGGER.warn("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
    }

    /**
     * Deletes an employee by its ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws NoSuchElementException if no employee is found with the given ID.
     */
    public void deleteEmployee(Long id) {
        if (!this.employeeRepository.existsById(id)) {
            LOGGER.error("Employee not found with id: {}", id);
            throw new NoSuchElementException("Employee not found with id: " + id);
        }
        LOGGER.info("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
        LOGGER.info("Employee deleted with ID: {}", id);
    }

    /**
     * Filters employees based on provided department, job title, and salary.
     *
     * @param department The department to filter by (optional).
     * @param jobTitle The job title to filter by (optional).
     * @param salary The minimum salary to filter by (optional).
     * @return A list of employees that match the filter criteria.
     */
    public List<Employee> filterEmployees(String department, String jobTitle, Double salary) {
        LOGGER.info("Filtering employees with department: {}, job title: {}, salary: {}", department, jobTitle, salary);
        return employeeRepository.filterEmployees(department, jobTitle, salary);
    }

    /**
     * Retrieves employee count grouped by department.
     *
     * @return A list of object arrays containing department name and the corresponding employee count.
     */
    public List<Object[]> getEmployeeCountByDepartment() {
        LOGGER.info("Getting employees count by grouping department");
        return employeeRepository.countEmployeesByDepartment();
    }

    /**
     * Retrieves employee count grouped by job title.
     *
     * @return A list of object arrays containing job title and the corresponding employee count.
     */
    public List<Object[]> getEmployeeCountByJobTitle() {
        LOGGER.info("Getting employees count by grouping job title");
        return employeeRepository.countEmployeesByJobTitle();
    }
}
