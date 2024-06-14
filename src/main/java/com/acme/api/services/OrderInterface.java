package com.acme.api.services;

import com.acme.api.dto.GetAllOrdersDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;

import java.util.stream.Stream;

public interface OrderInterface {
    Order createOrder(OrderRequestBody orderRequestBody);
    Stream<GetAllOrdersDTO> getAllOrders();
    Order getOrder(long id);
    void deleteOrder(long id);
    Order updateOrder(Long id, OrderRequestBody orderRequestBody);
    Order getOrCreateOrder(Order order);
}
