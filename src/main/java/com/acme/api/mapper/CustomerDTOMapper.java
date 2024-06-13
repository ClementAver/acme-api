package com.acme.api.mapper;

import com.acme.api.dto.CustomerResponseBody;
import com.acme.api.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerDTOMapper implements Function<Customer, CustomerResponseBody> {
    @Override
    public CustomerResponseBody apply(Customer customer) {
        return new CustomerResponseBody(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
    }
}
