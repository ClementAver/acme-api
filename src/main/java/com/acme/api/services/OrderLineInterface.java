package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface OrderLineInterface {
    Stream<OrderLineDTO> getOrderLines();
    OrderLineDTO getOrderLine(Long id);
    OrderLineDTO createOrderLine(OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    OrderLineDTO updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    Long deleteOrderLine(Long id) throws ResponseStatusException;
}
