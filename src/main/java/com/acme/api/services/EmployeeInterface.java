package com.acme.api.services;

import com.acme.api.entities.Customer;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;

import java.util.List;

public interface EmployeeInterface {
    Employee createEmployee(EmployeeRequestBody employeeRequestBody);
    List<Employee> getAllEmployees();
    Employee getEmployee(long id);
    void deleteEmployee(long id);
    Employee updateEmployee(Long id, EmployeeRequestBody employeeRequestBody);
    Employee getOrCreateEmployee(Employee employee);
    }
