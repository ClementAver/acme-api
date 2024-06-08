package com.acme.api.dto;

import com.acme.api.entities.Customer;
import com.acme.api.entities.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequestBody {
    String date;
    Customer idCustomer;
    Set<OrderLine> orderlines;


    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


