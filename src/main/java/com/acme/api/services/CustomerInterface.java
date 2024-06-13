package com.acme.api.services;

import com.acme.api.dto.GetAllCustomersDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;

import java.util.stream.Stream;

public interface CustomerInterface {
    Customer createCustomer(CustomerRequestBody customerRequestBody);
    Stream<GetAllCustomersDTO> getAllCustomers();
    Customer getCustomer(long id);
    void deleteCustomer(long id);
    Customer updateCustomer(Long id, CustomerRequestBody customerRequestBody);
    Customer getOrCreateCustomer(Customer customer);
}
