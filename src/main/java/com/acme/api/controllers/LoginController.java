package com.acme.api.controllers;

import com.acme.api.dto.LoginRequestBody;
import com.acme.api.entities.Employee;
import com.acme.api.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;

    public LoginController(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
    }

    // @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestBody loginRequestBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            // Authentication : throws "AuthenticationException".
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBody.getUsername(), loginRequestBody.getPassword())
            );

            // If authentication successful :
            if (authentication.isAuthenticated()) {
                Employee employee = employeeRepository.findByUsername(loginRequestBody.getUsername());
                request.getSession(); // Highlighted modification: Create session
                return ResponseEntity.ok("Bienvenue, " + employee.getFirstName() + " " + employee.getLastName());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

