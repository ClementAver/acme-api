package com.acme.api.controllers;

import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product")
    public Product getProduct(@RequestParam(name = "id", required=true) long id) {
        Optional<Product> product = Optional.ofNullable(productService.getProduct(id));
        return product.orElse(null);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody ProductRequestBody productRequestBody) {
        return productService.createProduct(productRequestBody);
    }

    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam(name = "id", required=true) long id) {
        productService.deleteProduct(id);
    }
}

