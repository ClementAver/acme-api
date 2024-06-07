package com.acme.api.repositories;

import com.acme.api.entities.Order;
import com.acme.api.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    OrderLine findById(long id);
}