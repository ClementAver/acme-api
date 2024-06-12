package com.acme.api.repositories;

import com.acme.api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(long id);
    Customer findByEmail(String email);
}