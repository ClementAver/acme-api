package com.acme.api.services;

import com.acme.api.entities.Customer;
import com.acme.api.odt.CustomerRequestBody;
import com.acme.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerInterface{

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerRequestBody customerRequestBody) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequestBody.getFirstName());
        customer.setLastName(customerRequestBody.getLastName());
        customer.setPhone(customerRequestBody.getPhone());
        customer.setAddress(customerRequestBody.getAddress());
        customer.setEmail(customerRequestBody.getEmail());
        customer.setOrders(customerRequestBody.getOrders());
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById((long) id);
    }
}
