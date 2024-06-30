package com.acme.api.controllers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
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
    public ProductDTO getProduct(@RequestParam(value="reference") @Pattern(regexp="^PRO-\\d{13}$", message = "La référence produit passée en paramètre de la requête n'est pas valide.") String reference) throws NotFoundException {
        return productService.getProductByReference(reference);
    }

    // to be fixed.
    @GetMapping("/products/search")
    public Stream<ProductDTO> getProductsByName(@RequestParam(value="name") String name) throws NoMatchException {
        return productService.getProductsByName(name);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@Valid @RequestBody ProductRequestBody productRequestBody) {
           return productService.createProduct(productRequestBody);
    }

    @PutMapping(value = "/product", consumes = APPLICATION_JSON_VALUE)
    public ProductDTO updateProduct(@RequestParam(value="reference") @Pattern(regexp="^PRO-\\d{13}$", message = "La référence produit passée en paramètre de la requête n'est pas valide.") String reference, @Valid @RequestBody ProductRequestBody productRequestBody) throws NotFoundException {
            return productService.updateProduct(reference, productRequestBody);
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam(value="reference") @Pattern(regexp="^PRO-\\d{13}$", message = "La référence produit passée en paramètre de la requête n'est pas valide.") String reference) throws NotFoundException {
            return productService.deleteProduct(reference);
    }

    @GetMapping("/product/{reference}/order-lines")
    public Stream<OrderLineDTO> getOrderLinesFromProduct(@PathVariable @Pattern(regexp="^PRO-\\d{13}$", message = "La référence produit constituante du chemin d'accès de la requête n'est pas valide.") String reference) throws NoMatchException, NotFoundException {
        return productService.getOrderLinesFromProduct(reference);
    }
}

