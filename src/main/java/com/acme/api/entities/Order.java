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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false, length = 64)
    private String date;

    // Will only be fetched when getOrders is called on a customer.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_customer", nullable = false)
    @JsonIgnoreProperties("orders")
    private Customer idCustomer;

    // If not already, associated entities will also be persisted in the DB with this one.
    // If this entity is deleted, the entities that depend on it will also be deleted from the DB.
    @OneToMany(mappedBy = "idOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("idOrder")
    private Set<OrderLine> orderLines = new LinkedHashSet<>();
}