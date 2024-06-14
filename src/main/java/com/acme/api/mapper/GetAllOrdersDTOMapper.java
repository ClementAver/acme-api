package com.acme.api.mapper;

import com.acme.api.dto.GetAllOrdersDTO;
import com.acme.api.entities.Order;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GetAllOrdersDTOMapper implements Function<Order, GetAllOrdersDTO> {
    @Override
    public GetAllOrdersDTO apply(Order order) {
        return new GetAllOrdersDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
    }
}
