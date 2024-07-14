package com.acme.api.repositories;

import com.acme.api.entities.Customer;
import com.acme.api.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long id);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
}