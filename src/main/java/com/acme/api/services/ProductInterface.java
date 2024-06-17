package com.acme.api.services;

import com.acme.api.dto.GetAllProductsDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;

import java.util.stream.Stream;

public interface ProductInterface {
    Product createProduct(ProductRequestBody productRequestBody);
    Stream<GetAllProductsDTO> getAllProducts();
    // Product getProduct(long id);
    Product getProduct(String reference);
    void deleteProduct(long id);
    Product updateProduct(Long id, ProductRequestBody productRequestBody);
    Product getOrCreateProduct(Product product);
}
