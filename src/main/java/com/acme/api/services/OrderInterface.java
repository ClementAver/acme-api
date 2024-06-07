package com.acme.api.services;

import com.acme.api.entities.Order;
import com.acme.api.odt.OrderRequestBody;

import java.util.List;

public interface OrderInterface {
    Order createOrder(OrderRequestBody orderRequestBody);
    List<Order> getAllOrders();
    Order getOrder(int id);
    void deleteOrder(int id);
}
