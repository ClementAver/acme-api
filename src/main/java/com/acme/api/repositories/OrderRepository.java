package com.acme.api.repositories;

import com.acme.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);
    Optional<Order> findByReference(String reference);
    Set<Order> findAllByIdCustomer_Email(String email);
}