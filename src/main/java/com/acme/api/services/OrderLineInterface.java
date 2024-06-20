package com.acme.api.services;

import com.acme.api.dto.GetOrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface OrderLineInterface {
    Stream<GetOrderLineDTO> getOrderLines();
    Stream<GetOrderLineDTO> getOrderLinesFromOrder(String orderReference);
    Stream<GetOrderLineDTO> getOrderLinesFromProduct(String productReference);
    void createOrderLine(OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    void updateOrderLine(long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException;
    void deleteOrderLine(long id) throws ResponseStatusException;
}
