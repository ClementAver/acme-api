package com.acme.api.dto;

import com.acme.api.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetCustomerDTO {
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    Set<Order> orders;
}


