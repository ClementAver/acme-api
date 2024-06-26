package com.acme.api.mappers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.OrderLine;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class OrderLinesDTOMapper implements Function<OrderLine, OrderLineDTO> {
    @Override
    public OrderLineDTO apply(OrderLine orderLine) {
        return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
    }
}
