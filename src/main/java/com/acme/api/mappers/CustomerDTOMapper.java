package com.acme.api.mappers;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.entities.Customer;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {
    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
    }
}
