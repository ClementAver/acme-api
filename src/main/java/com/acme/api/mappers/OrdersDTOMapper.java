package com.acme.api.mappers;

import com.acme.api.dto.OrderDTO;
import com.acme.api.entities.Order;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class OrdersDTOMapper implements Function<Order, OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
    }
}
