package com.acme.api.repositories;

import com.acme.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    Product findByName(String name);
    Product findByReference(String reference);
    List<Product> findAllByName(String name);
}