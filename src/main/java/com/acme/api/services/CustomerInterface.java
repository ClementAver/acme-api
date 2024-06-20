package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface CustomerInterface {
    Stream<GetCustomerDTO> getCustomers();
    GetCustomerDTO getCustomerByEmail(String email) throws ResponseStatusException;
    void createCustomer(CustomerRequestBody customerRequestBody) throws ResponseStatusException;
    void deleteCustomer(String email) throws ResponseStatusException;
    void updateCustomer(String email, CustomerRequestBody customerRequestBody) throws ResponseStatusException;
    Customer getOrCreateCustomer(Customer customer);
}
