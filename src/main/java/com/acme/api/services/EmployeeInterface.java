package com.acme.api.services;

import com.acme.api.entities.Employee;
import com.acme.api.odt.EmployeeRequestBody;

import java.util.List;

public interface EmployeeInterface {
    Employee createEmployee(EmployeeRequestBody employeeRequestBody);
    List<Employee> getAllEmployees();
    Employee getEmployee(int id);
    void deleteEmployee(int id);
}
