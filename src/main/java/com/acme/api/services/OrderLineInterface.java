package com.acme.api.services;

import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;

import java.util.List;

public interface OrderLineInterface {
    OrderLine createOrderLine(OrderLineRequestBody orderLineRequestBody);
    List<OrderLine> getAllOrderLines();
    OrderLine getOrderLine(long id);
    void deleteOrderLine(long id);
}
