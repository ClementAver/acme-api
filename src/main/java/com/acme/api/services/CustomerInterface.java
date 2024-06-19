package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import java.util.stream.Stream;

public interface CustomerInterface {
    void createCustomer(CustomerRequestBody customerRequestBody) throws Exception;
    Stream<GetCustomerDTO> getAllCustomers();
    GetCustomerDTO getCustomerByEmail(String email);
    void deleteCustomer(String email) throws Exception;
    void updateCustomer(String email, CustomerRequestBody customerRequestBody) throws Exception;
    Customer getOrCreateCustomer(Customer customer);
}
