package com.acme.api.services;

import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
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
        customer.setEmail(customerRequestBody.getEmail());
        customer.setPhone(customerRequestBody.getPhone());
        customer.setAddress(customerRequestBody.getAddress());
        customer.setOrders(customerRequestBody.getOrders());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequestBody customerRequestBody) {
        Customer customerToUpdate = customerRepository.getReferenceById(id);

        if(customerRequestBody.getFirstName() != null){
            customerToUpdate.setFirstName(customerRequestBody.getFirstName());
        }
        if(customerRequestBody.getLastName() != null){
            customerToUpdate.setLastName(customerRequestBody.getLastName());
        }
        if(customerRequestBody.getEmail() != null){
            customerToUpdate.setEmail(customerRequestBody.getEmail());
        }
        if(customerRequestBody.getPhone() != null){
            customerToUpdate.setPhone(customerRequestBody.getPhone());
        }
        if(customerRequestBody.getAddress() != null){
            customerToUpdate.setAddress(customerRequestBody.getAddress());
        }
        return customerRepository.save(customerToUpdate);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById((long) id);
    }
}
