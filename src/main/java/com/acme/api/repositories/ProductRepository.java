package com.acme.api.repositories;

import com.acme.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    Optional<Product> findByReference(String reference);
//    @Query(value = "SELECT * FROM products WHERE name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
//    Optional<Set<Product>> findProductsByNameContainingIgnoreCase(@Param("name") String name);
    Optional<Set<Product>> findProductsByNameContainingIgnoreCase(String name);
}