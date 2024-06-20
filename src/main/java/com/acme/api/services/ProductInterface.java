package com.acme.api.services;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface ProductInterface {
    Stream<GetProductDTO> getProducts();
    Stream<GetProductDTO> getProductsByName(String name);
    Product getProductEntity(String reference) throws ResponseStatusException;
    GetProductDTO getProductByReference(String reference) throws ResponseStatusException;
    void createProduct(ProductRequestBody productRequestBody);
    void updateProduct(String reference, ProductRequestBody productRequestBody) throws ResponseStatusException;
    void deleteProduct(String reference) throws ResponseStatusException;
    Product getOrCreateProduct(Product product);
}
