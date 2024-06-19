package com.acme.api.controllers;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public Stream<GetEmployeeDTO> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee")
    public GetEmployeeDTO getEmployee(@RequestParam(name = "username", required=true) String username) {
        return employeeService.getEmployeeByUsername(username);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public Employee createEmployee(@RequestBody EmployeeRequestBody employeeRequestBody) {
        return employeeService.createEmployee(employeeRequestBody);
    }

    @PutMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public Employee updateEmployee(@RequestParam(name = "id", required=true) long id, @RequestBody EmployeeRequestBody employeeRequestBody) {
        return employeeService.updateEmployee(id, employeeRequestBody);
    }

    @DeleteMapping("/employee")
    public void deleteEmployee(@RequestParam(name = "id", required=true) long id) {
        employeeService.deleteEmployee(id);
    }
}



