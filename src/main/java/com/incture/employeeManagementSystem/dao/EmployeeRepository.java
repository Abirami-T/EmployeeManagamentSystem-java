package com.incture.employeeManagementSystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incture.employeeManagementSystem.entities.Employee;

/**
 * Repository interface for performing CRUD operations on the Employee entity.
 * This interface extends JpaRepository to provide built-in methods for data access.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Filters employees based on department, job title, and salary.
     * If any parameter is null, it is ignored in the filter.
     * 
     * @param department The department to filter by (can be null).
     * @param jobTitle The job title to filter by (can be null).
     * @param salary The minimum salary to filter by (can be null).
     * @return A list of employees that match the specified filters.
     */
    @Query("SELECT e FROM Employee e WHERE (:department IS NULL OR e.department LIKE %:department%) " +
           "AND (:jobTitle IS NULL OR e.jobTitle LIKE %:jobTitle%) " +
           "AND (:salary IS NULL OR e.salary >= :salary)")
    List<Employee> filterEmployees(
            @Param("department") String department,
            @Param("jobTitle") String jobTitle,
            @Param("salary") Double salary
    );

    /**
     * Retrieves the number of employees grouped by department.
     * 
     * @return A list of Object arrays, each containing a department name and the number of employees in that department.
     */
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countEmployeesByDepartment();

    /**
     * Retrieves the number of employees grouped by job title.
     * 
     * @return A list of Object arrays, each containing a job title and the number of employees with that job title.
     */
    @Query("SELECT e.jobTitle, COUNT(e) FROM Employee e GROUP BY e.jobTitle")
    List<Object[]> countEmployeesByJobTitle();
}

