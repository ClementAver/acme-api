package com.acme.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends Individual{
    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @Column(name = "email", nullable = false, length = 64, unique = true)
    private String email;

    // If not already, associated entities will also be persisted in the DB with this one.
    // If this entity is deleted, the entities that depend on it will also be deleted from the DB.
    @OneToMany(mappedBy = "idCustomer", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();
}