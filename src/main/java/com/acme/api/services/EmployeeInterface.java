package com.acme.api.services;

import com.acme.api.dto.EmployeeDTO;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NotFoundException;

import java.util.stream.Stream;

public interface EmployeeInterface {
    Stream<EmployeeDTO> getEmployees();
    EmployeeDTO getEmployeeByEmail(String email) throws NotFoundException;
    EmployeeDTO createEmployee(EmployeeRequestBody employeeRequestBody) throws AlreadyExistException;
    EmployeeDTO updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws NotFoundException;
    String deleteEmployee(String email) throws NotFoundException;
    // Employee getOrCreateEmployee(Employee employee);
    }
