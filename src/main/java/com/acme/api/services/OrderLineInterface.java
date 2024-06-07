package com.acme.api.services;

import com.acme.api.entities.OrderLine;
import com.acme.api.odt.OrderLineRequestBody;

import java.util.List;

public interface OrderLineInterface {
    OrderLine createOrderLine(OrderLineRequestBody orderLineRequestBody);
    List<OrderLine> getAllOrderLines();
    OrderLine getOrderLine(int id);
    void deleteOrderLine(int id);
}
