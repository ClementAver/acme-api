package com.acme.api.mapper;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class GetCustomerDTOMapper implements Function<Customer, GetCustomerDTO> {
    @Override
    public GetCustomerDTO apply(Customer customer) {
        return new GetCustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
    }
}
