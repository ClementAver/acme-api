package com.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderLineDTO {
    Long id;
    Integer quantity;
    String referenceProduct;
    String referenceOrder;
}


