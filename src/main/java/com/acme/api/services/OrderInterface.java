package com.acme.api.services;

import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;

import java.util.List;

public interface OrderInterface {
    Order createOrder(OrderRequestBody orderRequestBody);
    List<Order> getAllOrders();
    Order getOrder(long id);
    void deleteOrder(long id);
    Order updateOrder(Long id, OrderRequestBody orderRequestBody);
}
