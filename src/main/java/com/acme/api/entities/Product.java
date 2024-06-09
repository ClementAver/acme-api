package com.acme.api.entities;

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

    @OneToMany(mappedBy = "idProduct")
    private Set<OrderLine> orderlines = new LinkedHashSet<>();

}