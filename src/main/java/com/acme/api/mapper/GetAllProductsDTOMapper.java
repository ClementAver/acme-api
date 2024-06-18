package com.acme.api.mapper;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class GetAllProductsDTOMapper implements Function<Product, GetProductDTO> {
    @Override
    public GetProductDTO apply(Product product) {
        return new GetProductDTO(product.getReference(), product.getName(), product.getPrice());
    }
}
