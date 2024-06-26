package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface OrderLineInterface {
    Stream<OrderLineDTO> getOrderLines();
    Stream<OrderLineDTO> getOrderLinesFromOrder(String orderReference);
    Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference);
    void createOrderLine(OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    void updateOrderLine(long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    void deleteOrderLine(long id) throws ResponseStatusException;
}
