package com.acme.api.controllers;

import com.acme.api.configuration.CustomUserDetailsService;
import com.acme.api.dto.LoginRequestBody;
import com.acme.api.entities.Employee;
import com.acme.api.repositories.EmployeeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping("/api")
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public LoginController(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestBody loginRequestBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            // Authentication : throws "AuthenticationException".
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBody.getUsername(), loginRequestBody.getPassword())
            );

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

            // If authentication successful :
            if (authentication.isAuthenticated()) {
                Employee employee = employeeRepository.findByUsername(loginRequestBody.getUsername());
                request.getSession(true);

                String sessionId = request.getSession().getId();
                logger.info("Session ID: {}", sessionId);  // Log the session ID

                return ResponseEntity.ok("Bienvenue, " + employee.getFirstName() + " " + employee.getLastName());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // Ajouter les en-têtes CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok("Logged out");
    }


}
