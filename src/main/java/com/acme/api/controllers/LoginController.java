package com.acme.api.controllers;

import com.acme.api.dto.LoginRequestBody;
import com.acme.api.entities.Employee;
import com.acme.api.repositories.EmployeeRepository;
import org.springframework.security.authentication.AuthenticationManager;
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

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestBody loginRequestBody) {
            // Authentication : throws "AuthenticationException".
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBody.getUsername(), loginRequestBody.getPassword())
            );

            // If authentication successful :
            if (authentication.isAuthenticated()) {
                Employee employee = employeeRepository.findByUsername(loginRequestBody.getUsername());
                return "Login successful";
            } else {
                return "Login failed";
            }
    }
}
