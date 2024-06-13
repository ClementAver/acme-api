package com.acme.api.services;

import com.acme.api.dto.CustomerResponseBody;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.mapper.CustomerDTOMapper;
import com.acme.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerService implements CustomerInterface{

    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDTOMapper customerDTOMapper) {
        this.customerRepository = customerRepository;
        this.customerDTOMapper = customerDTOMapper;
    }

    @Override
    public Customer createCustomer(CustomerRequestBody customerRequestBody) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequestBody.getFirstName());
        customer.setLastName(customerRequestBody.getLastName());
        customer.setEmail(customerRequestBody.getEmail());
        if(customerRequestBody.getPhone() != null){
            customer.setPhone(customerRequestBody.getPhone());
        }
        if(customerRequestBody.getAddress() != null){
            customer.setAddress(customerRequestBody.getAddress());
        }
        if(customerRequestBody.getOrders() != null){
            customer.setOrders(customerRequestBody.getOrders());
        }

        Customer customerInDB = customerRepository.findByEmail(customer.getEmail());
        try {
            if (customerInDB == null) {
                return customerRepository.save(customer);
            }
            throw new Exception("Cet email a déjà été renseigné.");
        } catch (Exception e) {
            return null;
        }
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
    public Stream<CustomerResponseBody> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(customerDTOMapper);
    }

    @Override
    public Customer getCustomer(long id) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(this.customerRepository.findById(id));
        return optionalCustomer.orElse(null);
    }

    @Override
    public Customer getOrCreateCustomer(Customer customer) {
        Customer customerInDB = customerRepository.findByEmail(customer.getEmail());
        if (customerInDB == null) {
            customerInDB = customerRepository.save(customer);
        }
        return customerInDB;
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
