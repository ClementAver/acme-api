package com.acme.api.dto;

import com.acme.api.entities.Order;
import com.acme.api.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderLineRequestBody {
    Integer quantity;
    Product idProduct;
    Order idOrder;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


