package com.acme.api.controllers;

import com.acme.api.configuration.CustomUserDetailsService;
import com.acme.api.dto.LoginRequestBody;
import com.acme.api.entities.Employee;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.repositories.EmployeeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestBody loginRequestBody, HttpServletRequest request, HttpServletResponse response) throws NoMatchException {

            // Authentication : throws "AuthenticationException".
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBody.getUsername(), loginRequestBody.getPassword())
            );

            // Set the session in the context.
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setHttpOnly(false);
            cookie.setSecure(false);

            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);

            response.addCookie(cookie);
            response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            // If authentication successful :
            Optional<Employee> employeeInDB = employeeRepository.findByUsername(loginRequestBody.getUsername());
            if (employeeInDB.isPresent()) {
                Employee employee = employeeInDB.get();
                return ResponseEntity.ok("Bienvenue, " + employee.getFirstName() + " " + employee.getLastName());
            } else {
                throw new NoMatchException("Employé non référencé.");
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
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok("Logged out");
    }


}
