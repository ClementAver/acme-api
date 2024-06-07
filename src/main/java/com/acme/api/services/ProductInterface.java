package com.acme.api.services;

import com.acme.api.entities.Product;
import com.acme.api.odt.ProductRequestBody;

import java.util.List;

public interface ProductInterface {
    Product createProduct(ProductRequestBody productRequestBody);
    List<Product> getAllProducts();
    Product getProduct(int id);
    void deleteProduct(int id);
}
