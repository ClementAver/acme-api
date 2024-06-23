package com.acme.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_line", nullable = false, unique = true)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Will only be fetched when getOrderLines is called on a Product.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    @JsonIgnoreProperties({"orderLines", "id"})
    private Product idProduct;

    // Will only be fetched when getOrderLines is called on an Order.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_order", nullable = false)
    @JsonIgnoreProperties({"orderLines", "id"})
    private Order idOrder;


    // Custom getters for DTO purpose.
    public String getProductReference() {
        return idProduct.getReference();
    }

    public String getOrderReference() {
        return idOrder.getReference();
    }
}