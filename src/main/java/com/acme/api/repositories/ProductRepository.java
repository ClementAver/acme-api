package com.acme.api.repositories;

import com.acme.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    Optional<Product> findByReference(String reference);
    Optional<Set<Product>> findProductsByNameContainingIgnoreCase(String name);
}