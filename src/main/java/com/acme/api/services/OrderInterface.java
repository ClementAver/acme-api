package com.acme.api.services;

import com.acme.api.dto.GetOrderDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import java.util.stream.Stream;

public interface OrderInterface {
    Order createOrder(OrderRequestBody orderRequestBody);
    Stream<GetOrderDTO> getAllOrders();
    Stream<GetOrderDTO> getAllOrdersFromCustomer(String customerEmail);
    Order getOrderEntity(String reference);
    GetOrderDTO getOrder(String reference);
    void deleteOrder(long id);
    Order updateOrder(Long id, OrderRequestBody orderRequestBody);
    Order getOrCreateOrder(Order order);


}
