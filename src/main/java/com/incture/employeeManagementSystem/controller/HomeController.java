package com.incture.employeeManagementSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	/**
	 * Home page content after login
	 * @return
	 */
    @GetMapping("/home")
    public String home() {
        return "Welcome to the Home page!";
    }
    
    /**
     * After logout end page content
     * @return
     */
    @GetMapping("/end")
    public String end() {
        return "Thank You!";
    }
}

