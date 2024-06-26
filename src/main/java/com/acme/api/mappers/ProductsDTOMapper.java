package com.acme.api.mappers;

import com.acme.api.dto.ProductDTO;
import com.acme.api.entities.Product;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class ProductsDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(product.getReference(), product.getName(), product.getPrice());
    }
}
