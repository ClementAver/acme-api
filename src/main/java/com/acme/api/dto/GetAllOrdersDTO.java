package com.acme.api.dto;

import com.acme.api.entities.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrdersDTO {
    String reference;
    String date;
    String emailCustomer;
    Set<OrderLine> orderLines;
}


