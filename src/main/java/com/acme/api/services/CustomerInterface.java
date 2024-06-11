package com.acme.api.services;

import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;

import java.util.List;

public interface CustomerInterface {
    Customer createCustomer(CustomerRequestBody customerRequestBody);
    List<Customer> getAllCustomers();
    Customer getCustomer(long id);
    void deleteCustomer(long id);
    Customer updateCustomer(Long id, CustomerRequestBody customerRequestBody);
}
