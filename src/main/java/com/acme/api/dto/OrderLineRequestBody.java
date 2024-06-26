package com.acme.api.dto;

import com.acme.api.entities.Order;
import com.acme.api.entities.Product;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class OrderLineRequestBody {
    // 100K unit max.
    @Digits(integer = 6, fraction = 0)
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


