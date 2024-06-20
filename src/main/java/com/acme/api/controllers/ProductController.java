package com.acme.api.controllers;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.services.ProductService;
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
    public Stream<GetProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product")
    public GetProductDTO getProduct(@RequestParam(name = "reference", required=true) String reference) {
        try {
        return productService.getProductByReference(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/products-by-name")
    public Stream<GetProductDTO> getProductsByName(@RequestParam(name = "name", required=true) String name) {
        return productService.getProductsByName(name);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public String createProduct(@RequestBody ProductRequestBody productRequestBody) {
        try {
            productService.createProduct(productRequestBody);
            return "Création effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestParam(name = "reference", required=true) String reference, @RequestBody ProductRequestBody productRequestBody) {
        try {
            productService.updateProduct(reference, productRequestBody);
            return "Mise à jour effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam(name = "reference", required=true) String reference) {
        try {
            productService.deleteProduct(reference);
            return "Supression effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

