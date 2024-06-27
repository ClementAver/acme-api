package com.acme.api.repositories;

import com.acme.api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(long id);
    Optional<Customer> findByEmail(String email);
    void deleteByEmail(String email);
}