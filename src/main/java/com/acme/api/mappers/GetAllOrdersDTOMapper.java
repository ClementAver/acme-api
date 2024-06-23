package com.acme.api.mappers;

import com.acme.api.dto.GetOrderDTO;
import com.acme.api.entities.Order;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class GetAllOrdersDTOMapper implements Function<Order, GetOrderDTO> {
    @Override
    public GetOrderDTO apply(Order order) {
        return new GetOrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
    }
}
