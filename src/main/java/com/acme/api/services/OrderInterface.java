package com.acme.api.services;

import com.acme.api.dto.GetOrderDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface OrderInterface {
    Stream<GetOrderDTO> getOrders();
    Stream<GetOrderDTO> getOrdersFromCustomer(String customerEmail);
    Order getOrderEntity(String reference) throws ResponseStatusException;
    GetOrderDTO getOrderByReference(String reference) throws ResponseStatusException;
    void createOrder(OrderRequestBody orderRequestBody) throws ResponseStatusException;
    void updateOrder(String reference, OrderRequestBody orderRequestBody) throws ResponseStatusException;
    void deleteOrder(String reference) throws ResponseStatusException;
    Order getOrCreateOrder(Order order);
}
