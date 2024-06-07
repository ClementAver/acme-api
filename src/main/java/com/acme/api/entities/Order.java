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
@Table(name = "orders", schema = "acme")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrder", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false, length = 64)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCustomer", nullable = false)
    private Customer idCustomer;

    @OneToMany(mappedBy = "idOrder")
    private Set<Orderline> orderlines = new LinkedHashSet<>();

}