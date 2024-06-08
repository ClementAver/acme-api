package com.acme.api.controllers;

import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    // @Autowired if no constructor.
    final private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee")
    public Employee getEmployee(@RequestParam(name = "id", required=true) long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody EmployeeRequestBody employeeRequestBody) {
        return employeeService.createEmployee(employeeRequestBody);
    }

    @DeleteMapping("/employee")
    public void deleteEmployee(@RequestParam(name = "id", required=true) long id) {
        employeeService.deleteEmployee(id);
    }
}

