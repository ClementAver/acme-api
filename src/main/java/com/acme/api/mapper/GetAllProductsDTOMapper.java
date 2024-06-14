package com.acme.api.mapper;

import com.acme.api.dto.GetAllOrdersDTO;
import com.acme.api.dto.GetAllProductsDTO;
import com.acme.api.entities.Order;
import com.acme.api.entities.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GetAllProductsDTOMapper implements Function<Product, GetAllProductsDTO> {
    @Override
    public GetAllProductsDTO apply(Product product) {
        return new GetAllProductsDTO(product.getReference(), product.getName(), product.getPrice());
    }
}
