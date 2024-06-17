package com.acme.api.services;

import com.acme.api.dto.GetAllOrderLinesDTO;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;

import java.util.stream.Stream;

public interface OrderLineInterface {
    OrderLine createOrderLine(OrderLineRequestBody orderLineRequestBody);
    Stream<GetAllOrderLinesDTO> getAllOrderLines();
    OrderLine getOrderLine(long id);
    void deleteOrderLine(long id);
    OrderLine updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody);
    Stream<GetAllOrderLinesDTO> getAllOrderLinesFromOrder(String orderReference);
}
