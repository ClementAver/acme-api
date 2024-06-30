package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.exceptions.NotFoundException;
import java.util.stream.Stream;

public interface OrderLineInterface {
    Stream<OrderLineDTO> getOrderLines();
    OrderLineDTO getOrderLine(Long id) throws NotFoundException;
    OrderLineDTO createOrderLine(OrderLineRequestBody orderLineRequestBody) throws NotFoundException;
    OrderLineDTO updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) throws NotFoundException;
    Long deleteOrderLine(Long id) throws NotFoundException;
}
