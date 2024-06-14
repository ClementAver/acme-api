package com.acme.api.controllers;

import com.acme.api.dto.GetAllEmployeesDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public Stream<GetAllEmployeesDTO> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee")
    public Employee getEmployee(@RequestParam(name = "id", required=true) long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployee(id));
        return employee.orElse(null);
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



