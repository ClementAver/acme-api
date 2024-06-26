package com.acme.api.controllers;

import com.acme.api.dto.EmployeeDTO;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.services.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    // @Autowired if no constructor.
    final private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Stream<EmployeeDTO> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/employee")
    public EmployeeDTO getEmployee(@Valid @PathParam(value="email") @Email String email) {
        try {
            return employeeService.getEmployeeByEmail(email);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeRequestBody employeeRequestBody) {
        try {
            return employeeService.createEmployee(employeeRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public EmployeeDTO updateEmployee(@Valid @PathParam(value="email") @Email String email, @Valid @RequestBody EmployeeRequestBody employeeRequestBody) {
        try {
            return employeeService.updateEmployee(email, employeeRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/employee")
    public String deleteEmployee(@Valid @PathParam(value="email") @Email String email) {
        try {
            return employeeService.deleteEmployee(email);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}



