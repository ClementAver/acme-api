package com.acme.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    // If not already, associated entities will also be persisted in the DB with this one.
    // If this entity is deleted, the entities that depend on it will also be deleted from the DB.
    @OneToMany(mappedBy = "idProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("idProduct")
    private Set<OrderLine> orderLines = new LinkedHashSet<>();

}