package com.acme.api.entities;

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

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @OneToMany(mappedBy = "idCustomer")
    private Set<Order> orders = new LinkedHashSet<>();
}