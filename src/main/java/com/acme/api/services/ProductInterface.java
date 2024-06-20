package com.acme.api.services;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import java.util.stream.Stream;

public interface ProductInterface {
    Stream<GetProductDTO> getProducts();
    Stream<GetProductDTO> getProductsByName(String name);
    Product getProductEntity(String reference) throws Exception;
    GetProductDTO getProductByReference(String reference) throws Exception;
    void createProduct(ProductRequestBody productRequestBody);
    void updateProduct(String reference, ProductRequestBody productRequestBody) throws Exception;
    void deleteProduct(String reference) throws Exception;
    Product getOrCreateProduct(Product product);
}
