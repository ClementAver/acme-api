package com.acme.api.services;

import com.acme.api.dto.OrderDTO;
import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import java.util.stream.Stream;

public interface OrderInterface {
    Stream<OrderDTO> getOrders();
    Order getOrderEntity(String reference) throws NotFoundException;
    OrderDTO getOrderByReference(String reference) throws NotFoundException;
    OrderDTO createOrder(OrderRequestBody orderRequestBody) throws NotFoundException;
    OrderDTO updateOrder(String reference, OrderRequestBody orderRequestBody) throws NotFoundException;
    String deleteOrder(String reference) throws NotFoundException;
    // Order getOrCreateOrder(Order order);
    Stream<OrderLineDTO> getOrderLinesFromOrder(String orderReference) throws NoMatchException, NotFoundException;
}
