package com.acme.api.repositories;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    Optional<OrderLine> findById(Long id);
    Set<OrderLine> findAllByIdOrder_Reference(String orderReference);
    Set<OrderLine> findAllByIdProduct_Reference(String productReference);
}