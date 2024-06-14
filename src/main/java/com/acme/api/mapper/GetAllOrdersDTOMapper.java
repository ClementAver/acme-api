package com.acme.api.mapper;

import com.acme.api.dto.GetAllCustomersDTO;
import com.acme.api.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GetAllOrdersDTOMapper implements Function<Customer, GetAllCustomersDTO> {
    @Override
    public GetAllCustomersDTO apply(Customer customer) {
        return new GetAllCustomersDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
    }
}
