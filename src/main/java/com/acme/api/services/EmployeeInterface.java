package com.acme.api.services;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import java.util.stream.Stream;

public interface EmployeeInterface {
    Stream<GetEmployeeDTO> getEmployees();
    GetEmployeeDTO getEmployeeByEmail(String email) throws Exception;
    GetEmployeeDTO getEmployeeByUsername(String username) throws Exception;
    void createEmployee(EmployeeRequestBody employeeRequestBody) throws Exception;
    void updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws Exception;
    void deleteEmployee(String email) throws Exception;
    Employee getOrCreateEmployee(Employee employee);
    }
