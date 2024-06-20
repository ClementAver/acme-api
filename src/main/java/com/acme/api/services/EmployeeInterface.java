package com.acme.api.services;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface EmployeeInterface {
    Stream<GetEmployeeDTO> getEmployees();
    GetEmployeeDTO getEmployeeByEmail(String email) throws ResponseStatusException;
    GetEmployeeDTO getEmployeeByUsername(String username) throws ResponseStatusException;
    void createEmployee(EmployeeRequestBody employeeRequestBody) throws ResponseStatusException;
    void updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws ResponseStatusException;
    void deleteEmployee(String email) throws ResponseStatusException;
    Employee getOrCreateEmployee(Employee employee);
    }
