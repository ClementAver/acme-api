package com.acme.api.mappers;

import com.acme.api.dto.GetOrderLineDTO;
import com.acme.api.entities.OrderLine;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class GetAllOrderLinesDTOMapper implements Function<OrderLine, GetOrderLineDTO> {
    @Override
    public GetOrderLineDTO apply(OrderLine orderline) {
        return new GetOrderLineDTO(orderline.getId(), orderline.getQuantity(), orderline.getProductReference(), orderline.getOrderReference());
    }
}
