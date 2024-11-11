Employee Management System
Overview
This Employee Management System is a Spring Boot application that allows you to manage employees within an organization. 

This project uses Spring Security for authentication and authorization, Spring Data JPA for database interaction, MySQL for data persistence, and Postman for API testing.

Features
CRUD operations for managing employee data.
Role-based access control (Admin, Manager, Employee).
Ability to filter employees based on department, job title, and salary.
APIs for employee statistics (employee count by department and job title).
Prerequisites
JDK 21 installed.
Eclipse IDE used(any code editor can use)
Maven (for building the project).
MySQL database setup.
Postman for testing the APIs.

Project Setup

step 1. Clone the Repository
git clone https://github.com/Abirami-T/EmployeeManagamentSystem-java.git
cd employee-management-system

step 2. Set Up MySQL Database
Make sure MySQL is installed and running. Then create a database:
CREATE DATABASE employee_system;

3. Configure Database Connection
In your application.properties, 
configure the database connection, Logging and Monitoring,



# Database Configuration
spring.application.name=employeeManagementSystem
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/employee_system
spring.datasource.username=root
spring.datasource.password=Abirami@0202
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update


#others
spring.jpa.show-sql=true 

#Logger Configuration
logging.file.path=logs 
logging.file.name=logs/$employeeManagementSystem.log
logging.logback.rollingpolicy.file-name-pattern=${logging.file.name}-%d{yyyy-MM-dd}-%i.log
logging.logback.rollingpolicy.max-file-size=1KB
logging.level.com.incture.employeeManagementSystem.service=trace 

#Monitoring
management.endpoints.web.base-path=/admin
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

Step 4. Start the springboot Application
Build and run the application using Maven

Step 5. Authentication and Authorization
Admin Role: Full access to all employee endpoints(crud).
Manager Role: Limited access to certain view endpoints.
Employee Role: Access to only personal profile data.

post - /auth/register - register user 
post - /auth/login - Login with session management security
get - /auth/logout - logout the session

based on logged in user's role api endpoint will have access

For Admin role -> /employees/** (can able to perform all functions)
For Manager role -> /view/** (can view all employee details/ access by id)
For Employee role -> /profile/{id} (only view by id)


Error Handling
The system handles errors gracefully. For example:

Employee not found: If an employee with a given ID does not exist, the system returns a 404 Not Found error.
Internal Server Error: In case of unexpected issues, the system returns a 500 Internal Server Error.

Conclusion
This Employee Management System provides a full-fledged backend with CRUD operations, filtering, and statistics. It is designed with Spring Boot, Spring Security, and can be tested with Postman for easy interaction with the API.