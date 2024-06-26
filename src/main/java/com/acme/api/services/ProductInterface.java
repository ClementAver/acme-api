package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface ProductInterface {
    Stream<ProductDTO> getProducts();
    Stream<ProductDTO> getProductsByName(String name);
    Product getProductEntity(String reference) throws ResponseStatusException;
    ProductDTO getProductByReference(String reference) throws ResponseStatusException;
    ProductDTO createProduct(ProductRequestBody productRequestBody);
    ProductDTO updateProduct(String reference, ProductRequestBody productRequestBody) throws ResponseStatusException;
    String deleteProduct(String reference) throws ResponseStatusException;
    Product getOrCreateProduct(Product product);
    Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference);
}
