package com.acme.api.services;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.dto.OrderDTO;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;

import java.util.stream.Stream;

public interface CustomerInterface {
    Stream<CustomerDTO> getCustomers();
    CustomerDTO getCustomerByEmail(String email) throws NotFoundException;
    CustomerDTO createCustomer(CustomerRequestBody customerRequestBody) throws AlreadyExistException;
    CustomerDTO updateCustomer(String email, CustomerRequestBody customerRequestBody) throws NoMatchException, NotFoundException;
    String deleteCustomer(String email) throws NoMatchException, NotFoundException;
    Stream<OrderDTO> getOrdersFromCustomer(String email) throws NotFoundException, NoMatchException;
    // Customer getOrCreateCustomer(Customer customer);

}
