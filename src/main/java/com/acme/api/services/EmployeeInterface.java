package com.acme.api.services;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import java.util.stream.Stream;

public interface EmployeeInterface {
    Employee createEmployee(EmployeeRequestBody employeeRequestBody);
    Stream<GetEmployeeDTO> getAllEmployees();
    GetEmployeeDTO getEmployeeByUsername(String username);
    void deleteEmployee(String email) throws Exception;
    void updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws Exception;
    Employee getOrCreateEmployee(Employee employee);
    }
