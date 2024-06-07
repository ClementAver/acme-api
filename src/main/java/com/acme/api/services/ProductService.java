package com.acme.api.services;

import com.acme.api.entities.Product;
import com.acme.api.odt.ProductRequestBody;
import com.acme.api.repositories.ProductRepository;
import com.acme.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductInterface{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductRequestBody productRequestBody) {
        Product product = new Product();
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById((long) id);
    }
}
