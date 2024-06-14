package com.acme.api.mapper;

import com.acme.api.dto.GetAllOrderLinesDTO;
import com.acme.api.entities.OrderLine;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GetAllOrderLinesDTOMapper implements Function<OrderLine, GetAllOrderLinesDTO> {
    @Override
    public GetAllOrderLinesDTO apply(OrderLine orderline) {
        return new GetAllOrderLinesDTO(orderline.getQuantity(), orderline.getProductReference(), orderline.getOrderReference());
    }
}
