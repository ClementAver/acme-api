package com.acme.api.services;

import com.acme.api.dto.GetOrderDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import java.util.stream.Stream;

public interface OrderInterface {
    Stream<GetOrderDTO> getOrders();
    Stream<GetOrderDTO> getOrdersFromCustomer(String customerEmail);
    Order getOrderEntity(String reference) throws Exception;
    GetOrderDTO getOrderByReference(String reference) throws Exception;
    void createOrder(OrderRequestBody orderRequestBody) throws Exception;
    void updateOrder(String reference, OrderRequestBody orderRequestBody) throws Exception;
    void deleteOrder(String reference) throws Exception;
    Order getOrCreateOrder(Order order);
}
