package com.acme.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false, unique = true)
    private Long id;

    // UUID type Generated Value
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "date", nullable = false, length = 64)
    private String date;

    // PERSIST = a customer will be created too / MERGE = a customer will be updated too.
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "id_customer", nullable = false)
    @JsonIgnoreProperties({"orders", "id"})
    private Customer idCustomer;

    // If not already, associated entities will also be persisted in the DB with this one.
    // If this entity is deleted, the entities that depend on it will also be deleted from the DB.
    @OneToMany(mappedBy = "idOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"idOrder"})
    private Set<OrderLine> orderLines = new LinkedHashSet<>();

    public static String generateReference() {
        return "ORD-" + new Date().getTime();
    }
}