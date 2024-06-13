package com.acme.api.services;

import com.acme.api.dto.CustomerResponseBody;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;

import java.util.List;
import java.util.stream.Stream;

public interface CustomerInterface {
    Customer createCustomer(CustomerRequestBody customerRequestBody);
    Stream<CustomerResponseBody> getAllCustomers();
    Customer getCustomer(long id);
    void deleteCustomer(long id);
    Customer updateCustomer(Long id, CustomerRequestBody customerRequestBody);
    Customer getOrCreateCustomer(Customer customer);
}
