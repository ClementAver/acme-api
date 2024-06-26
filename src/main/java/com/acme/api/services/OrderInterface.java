package com.acme.api.services;

import com.acme.api.dto.OrderDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface OrderInterface {
    Stream<OrderDTO> getOrders();
    Stream<OrderDTO> getOrdersFromCustomer(String customerEmail);
    Order getOrderEntity(String reference) throws ResponseStatusException;
    OrderDTO getOrderByReference(String reference) throws ResponseStatusException;
    OrderDTO createOrder(OrderRequestBody orderRequestBody) throws ResponseStatusException;
    OrderDTO updateOrder(String reference, OrderRequestBody orderRequestBody) throws ResponseStatusException;
    String deleteOrder(String reference) throws ResponseStatusException;
    Order getOrCreateOrder(Order order);
}
