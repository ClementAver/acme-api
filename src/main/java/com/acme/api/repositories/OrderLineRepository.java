package com.acme.api.repositories;

import com.acme.api.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    OrderLine findById(long id);
    Set<OrderLine> findAllByIdOrder_Reference(String orderReference);
}