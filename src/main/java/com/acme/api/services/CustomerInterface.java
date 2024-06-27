package com.acme.api.services;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.dto.OrderDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

public interface CustomerInterface {
    Stream<CustomerDTO> getCustomers();
    CustomerDTO getCustomerByEmail(String email) throws ResponseStatusException;
    CustomerDTO createCustomer(CustomerRequestBody customerRequestBody) throws ResponseStatusException;
    CustomerDTO updateCustomer(String email, CustomerRequestBody customerRequestBody) throws ResponseStatusException;
    String deleteCustomer(String email) throws ResponseStatusException;
    Stream<OrderDTO> getOrdersFromCustomer(String email);
    // Customer getOrCreateCustomer(Customer customer);

}
