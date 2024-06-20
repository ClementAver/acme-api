package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import java.util.stream.Stream;

public interface CustomerInterface {
    Stream<GetCustomerDTO> getCustomers();
    GetCustomerDTO getCustomerByEmail(String email) throws Exception;
    void createCustomer(CustomerRequestBody customerRequestBody) throws Exception;
    void deleteCustomer(String email) throws Exception;
    void updateCustomer(String email, CustomerRequestBody customerRequestBody) throws Exception;
    Customer getOrCreateCustomer(Customer customer);
}
