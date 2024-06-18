package com.acme.api.services;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import java.util.stream.Stream;

public interface EmployeeInterface {
    Employee createEmployee(EmployeeRequestBody employeeRequestBody);
    Stream<GetEmployeeDTO> getAllEmployees();
    Employee getEmployee(long id);
    GetEmployeeDTO getEmployeeByUsername(String username);
    void deleteEmployee(long id);
    Employee updateEmployee(Long id, EmployeeRequestBody employeeRequestBody);
    Employee getOrCreateEmployee(Employee employee);
    }
