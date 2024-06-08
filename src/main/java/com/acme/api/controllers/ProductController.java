package com.acme.api.controllers;

import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return productService.getProduct(id);
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody ProductRequestBody productRequestBody) {
        return productService.createProduct(productRequestBody);
    }

    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam(name = "id", required=true) long id) {
        productService.deleteProduct(id);
    }
}

