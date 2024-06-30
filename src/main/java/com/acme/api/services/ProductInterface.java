package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import java.util.stream.Stream;

public interface ProductInterface {
    Stream<ProductDTO> getProducts();
    Stream<ProductDTO> getProductsByName(String name) throws NoMatchException;
    Product getProductEntity(String reference) throws NotFoundException;
    ProductDTO getProductByReference(String reference) throws NotFoundException;
    ProductDTO createProduct(ProductRequestBody productRequestBody);
    ProductDTO updateProduct(String reference, ProductRequestBody productRequestBody) throws NotFoundException;
    String deleteProduct(String reference) throws NotFoundException;
    Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference) throws NoMatchException, NotFoundException;
}
