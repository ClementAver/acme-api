package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;

import java.util.stream.Stream;

public interface CustomerInterface {
    Customer createCustomer(CustomerRequestBody customerRequestBody);
    Stream<GetCustomerDTO> getAllCustomers();
    // Customer getCustomer(long id);
    GetCustomerDTO getCustomerByEmail(String email);
    void deleteCustomer(long id);
    Customer updateCustomer(Long id, CustomerRequestBody customerRequestBody);
    Customer getOrCreateCustomer(Customer customer);
}
