package com.incture.employeeManagementSystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // Constructor to inject UserDetailsService
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * @param http HttpSecurity to configure security for requests
     * @return Configured SecurityFilterChain
     * @throws Exception If there is any error during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (can be enabled later for added security)
            .authorizeHttpRequests(auth -> auth
            		  .requestMatchers("/auth/register").permitAll()  // Allow user registration
                .requestMatchers("/auth/login").permitAll()  // Allow login
                .requestMatchers("/employees/**").hasRole("Admin")  // Only Admin can access employees endpoints
                .requestMatchers("/view/**").hasAnyRole("Manager")  // Admin and Manager can view employees
                .requestMatchers("/profile/**").hasAnyRole("Employee")  // All roles can view profile
                .anyRequest().authenticated()  // Any other request must be authenticated
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/login")  // Custom login page URL
                .loginProcessingUrl("/auth/login")  // Endpoint to process login
                .defaultSuccessUrl("/home", true)  // Redirect on successful login
                .failureUrl("/auth/login?error=true")  // Redirect on login failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")  // Logout URL
                .logoutSuccessUrl("/end")  // Redirect after successful logout
                .invalidateHttpSession(true)  // Invalidate session on logout
                .permitAll()
            )
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)  // Limit to 1 session per user
                .expiredUrl("/auth/login?expired=true")  // Redirect when session expires
            );

        return http.build();
    }

    /**
     * Provides password encoder for encrypting passwords.
     * @return BCryptPasswordEncoder for password encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password encryption
    }

    /**
     * Provides an AuthenticationManager for authentication processes.
     * @param http HttpSecurity to extract AuthenticationManager
     * @return Configured AuthenticationManager
     * @throws Exception If there is an error during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
