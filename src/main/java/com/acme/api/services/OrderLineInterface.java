package com.acme.api.services;

import com.acme.api.dto.GetOrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import java.util.stream.Stream;

public interface OrderLineInterface {
    Stream<GetOrderLineDTO> getOrderLines();
    Stream<GetOrderLineDTO> getOrderLinesFromOrder(String orderReference);
    Stream<GetOrderLineDTO> getOrderLinesFromProduct(String productReference);
    void createOrderLine(OrderLineRequestBody orderLineRequestBody) throws Exception;
    void updateOrderLine(long id, OrderLineRequestBody orderLineRequestBody) throws Exception;
    void deleteOrderLine(long id) throws Exception;
}
