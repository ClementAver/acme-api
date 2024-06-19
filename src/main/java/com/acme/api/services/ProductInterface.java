package com.acme.api.services;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import java.util.stream.Stream;

public interface ProductInterface {
    Product createProduct(ProductRequestBody productRequestBody);
    Stream<GetProductDTO> getAllProducts();
    Product getProductEntity(String reference);
    Stream<GetProductDTO> getAllProductsByName(String name);
    GetProductDTO getProduct(String reference);
    void updateProduct(String reference, ProductRequestBody productRequestBody) throws Exception;
    Product getOrCreateProduct(Product product);
    void deleteProduct(String reference) throws Exception;
}
