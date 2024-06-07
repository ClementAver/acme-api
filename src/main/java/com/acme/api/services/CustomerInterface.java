package com.acme.api.services;

import com.acme.api.entities.Customer;
import com.acme.api.entities.Employee;
import com.acme.api.odt.CustomerRequestBody;
import com.acme.api.odt.EmployeeRequestBody;

import java.util.List;

public interface CustomerInterface {
    Customer createCustomer(CustomerRequestBody customerRequestBody);
    List<Customer> getAllCustomers();
    Customer getCustomer(long id);
    void deleteCustomer(long id);
}
