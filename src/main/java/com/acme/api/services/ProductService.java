package com.acme.api.services;

import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
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
        product.setName(productRequestBody.getName());
        product.setPrice(productRequestBody.getPrice());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById((long) id);
    }

    @Override
    public Product updateProduct(Long id, ProductRequestBody productRequestBody) {
        Product productToUpdate = productRepository.getReferenceById(id);
        if (productRequestBody.getName() != null) {
            productToUpdate.setName(productRequestBody.getName());
        }
        if (productRequestBody.getPrice() != null) {
            productToUpdate.setPrice(productRequestBody.getPrice());
        }
        return productRepository.save(productToUpdate);
    }
}
