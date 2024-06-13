package com.acme.api.services;

import com.acme.api.dto.GetAllCustomersDTO;
import com.acme.api.dto.GetAllEmployeesDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;

import java.util.stream.Stream;

public interface EmployeeInterface {
    Employee createEmployee(EmployeeRequestBody employeeRequestBody);
    Stream<GetAllEmployeesDTO> getAllEmployees();
    Employee getEmployee(long id);
    void deleteEmployee(long id);
    Employee updateEmployee(Long id, EmployeeRequestBody employeeRequestBody);
    Employee getOrCreateEmployee(Employee employee);
    }
