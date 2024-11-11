package com.incture.employeeManagementSystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.incture.employeeManagementSystem.entities.Employee;
import com.incture.employeeManagementSystem.service.EmployeeService;
import com.opencsv.CSVWriter;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	
	
	private final String UPLOAD_DIR = "uploads/"; // Directory where files are stored
	
	File directory = new File(UPLOAD_DIR);
	
	
    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint to retrieve all employees.
     * @return List of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
    	LOGGER.info("Getting all employees");
        return employeeService.getAllEmployees();
    }

    /**
     * Endpoint to retrieve a specific employee by ID.
     * @param id The ID of the employee
     * @return Employee object with the given ID
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
    	LOGGER.info("Getting employee with ID: {}", id);
        return employeeService.getEmployeeById(id);
    }

    /**
     * Endpoint to create a new employee.
     * @param employee Employee object containing the details to store
     * @return A success message
     */
    @PostMapping
    public String createEmployee(@RequestBody Employee employee) {
    	LOGGER.info("Creating employee: {}", employee.getName());
        employeeService.createEmployee(employee);
        return "Employee Details Stored successfully!";
    }

    /**
     * Endpoint to update an existing employee by ID.
     * @param id The ID of the employee to update
     * @param employee The updated employee data
     * @return A success message
     */
    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    	LOGGER.info("Updating employee with ID: {}", id);
        employeeService.updateEmployee(id,employee);
        return "Employee details updated successfully!";
    }

    /**
     * Endpoint to delete an employee by ID.
     * @param id The ID of the employee to delete
     * @return A success message
     */
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
    	LOGGER.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully!";
    }
    /**
     * Endpoint to filter employees based on department, job title, or salary.
     * @param department Optional department filter
     * @param jobTitle Optional job title filter
     * @param salary Optional salary filter
     * @return Filtered list of employees
     */
    
    @GetMapping("/filter")
    public List<Employee> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) Double salary) {
    	LOGGER.info("Filtering/Searching employees data");
        return employeeService.filterEmployees(department, jobTitle, salary);
    }
    
/**
 * Methods for exporting employee count based on grouping department in CSV format are also included,
 *  with logic to generate reports.
 * @return report
 * @throws IOException
 */
@GetMapping("/report/department/export")
public ResponseEntity<Resource> exportEmployeeCountByDepartment() throws IOException {
    // Create uploads directory if it doesn't exist
    File directory = new File(UPLOAD_DIR);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    // Define file path for the CSV
    Path filePath = Paths.get(UPLOAD_DIR).resolve("employee_count_by_department.csv");

    // Generate CSV data and write to the file
    List<Object[]> reportData = employeeService.getEmployeeCountByDepartment();
    try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toFile()))) {
        String[] header = { "Department", "Employee Count" };
        writer.writeNext(header);
        for (Object[] record : reportData) {
            String[] row = { (String) record[0], String.valueOf(record[1]) };
            writer.writeNext(row);
        }
        LOGGER.info("Department report generated successfully.");
    }

    // Serve the file for download
    Resource resource = new UrlResource(filePath.toUri());
    if (resource.exists() && resource.isReadable()) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    } else {
        return ResponseEntity.notFound().build();
    }
}
/**
 * Methods for exporting employee count based on grouping job title in CSV format are also included,
 *  with logic to generate reports.
 * @return
 * @throws IOException
 */
@GetMapping("/report/job-title/export")
public ResponseEntity<Resource> exportEmployeeCountByJobTitle() throws IOException {
    // Create uploads directory if it doesn't exist
    File directory = new File(UPLOAD_DIR);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    // Define file path for the CSV
    Path filePath = Paths.get(UPLOAD_DIR).resolve("employee_count_by_job_title.csv");

    // Generate CSV data and write to the file
    List<Object[]> reportData = employeeService.getEmployeeCountByJobTitle();
    try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toFile()))) {
        String[] header = { "Job Title", "Employee Count" };
        writer.writeNext(header);
        for (Object[] record : reportData) {
            String[] row = { (String) record[0], String.valueOf(record[1]) };
            writer.writeNext(row);
        }
        LOGGER.info("Job title report generated successfully.");
    }

    // Serve the file for download
    Resource resource = new UrlResource(filePath.toUri());
    if (resource.exists() && resource.isReadable()) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    } else {
        return ResponseEntity.notFound().build();
    }
}
    /**
     * Methods for exporting employee data in CSV format are also included,
     *  with logic to generate reports.
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/report")
    public ResponseEntity<Resource> generateReport(HttpServletResponse response) throws IOException {
    	 if (!directory.exists()) {
    	        directory.mkdirs();  // Create directory if it doesn't exist
    	    }
    	    
    	List<Employee> employees = employeeService.getAllEmployees();  // Get employees from DB

        // Define a temporary file path where the CSV will be saved
        Path filePath = Paths.get(UPLOAD_DIR).resolve("employees_report.csv");

        // Create the CSV file and write employee data
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toFile()))) {
            // Write the CSV header
            String[] header = { "ID", "Name", "Email", "Phone Number", "Salary", "Role", "Department", "Job Title" };
            writer.writeNext(header);

            // Write each employee record to CSV
            for (Employee employee : employees) {
                String[] data = { 
                    String.valueOf(employee.getId()), 
                    employee.getName(), 
                    employee.getEmail(), 
                    employee.getPhoneNumber(), 
                    String.valueOf(employee.getSalary()), 
                    employee.getRole(), 
                    employee.getDepartment(), 
                    employee.getJobTitle() 
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            LOGGER.error("Error generating employee report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Load the file as a Resource
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
