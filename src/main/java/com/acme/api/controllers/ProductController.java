package com.acme.api.controllers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.services.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class ProductController {

    // @Autowired if no constructor.
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Stream<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product")
    public ProductDTO getProduct(@Valid @PathParam(value="reference") String reference) {
        try {
        return productService.getProductByReference(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    // to be fixed.
    @GetMapping("/products/search")
    public Stream<ProductDTO> getProductsByName(@PathParam(value="name") String name) {
        return productService.getProductsByName(name);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@RequestBody ProductRequestBody productRequestBody) {
        try {
           return productService.createProduct(productRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public ProductDTO updateProduct(@Valid @PathParam(value="reference") String reference, @RequestBody ProductRequestBody productRequestBody) {
        try {
            return productService.updateProduct(reference, productRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/product")
    public String deleteProduct(@Valid @PathParam(value="reference") String reference) {
        try {
            return productService.deleteProduct(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/product/{reference}/order-lines")
    public Stream<OrderLineDTO> getOrderLinesFromProduct(@Valid @PathVariable String reference) {
        return productService.getOrderLinesFromProduct(reference);
    }
}

