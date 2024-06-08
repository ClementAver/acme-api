package com.acme.api.services;

import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;

import java.util.List;

public interface ProductInterface {
    Product createProduct(ProductRequestBody productRequestBody);
    List<Product> getAllProducts();
    Product getProduct(long id);
    void deleteProduct(long id);
}
