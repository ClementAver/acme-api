package com.acme.api.services;

import com.acme.api.dto.EmployeeDTO;
import com.acme.api.dto.EmployeeRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface EmployeeInterface {
    Stream<EmployeeDTO> getEmployees();
    EmployeeDTO getEmployeeByEmail(String email) throws ResponseStatusException;
    EmployeeDTO createEmployee(EmployeeRequestBody employeeRequestBody) throws ResponseStatusException;
    EmployeeDTO updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws ResponseStatusException;
    String deleteEmployee(String email) throws ResponseStatusException;
    // Employee getOrCreateEmployee(Employee employee);
    }
