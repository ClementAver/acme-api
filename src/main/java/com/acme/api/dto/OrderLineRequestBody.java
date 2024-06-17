package com.acme.api.dto;

import com.acme.api.entities.Order;
import com.acme.api.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderLineRequestBody {
    Integer quantity;
    Product idProduct;
    Order idOrder;

    public String getIdProductReference() {
        return idProduct.getReference();
    }

    public String getIdOrderReference() {
        return idOrder.getReference();
    }
}


