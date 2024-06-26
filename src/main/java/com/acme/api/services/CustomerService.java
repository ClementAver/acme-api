package com.acme.api.services;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.mappers.CustomerDTOMapper;
import com.acme.api.repositories.CustomerRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Stream<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream().map(customerDTOMapper);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) throws ResponseStatusException {
        Customer customerInDB = customerRepository.findByEmail(email);
        if (customerInDB == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Email inconnu.");
        }
        return new CustomerDTO(customerInDB.getFirstName(), customerInDB.getLastName(), customerInDB.getEmail(), customerInDB.getPhone(), customerInDB.getAddress());
    }

    @Override
    public CustomerDTO createCustomer(CustomerRequestBody customerRequestBody) throws ResponseStatusException {
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
        if (customerInDB != null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Cet email a déjà été renseigné.");
        }
        customerRepository.save(customer);
        return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());

    }

    @Override
    public CustomerDTO updateCustomer(String email, CustomerRequestBody customerRequestBody) throws ResponseStatusException {
            Customer customerToUpdate = customerRepository.findByEmail(email);
            if (customerToUpdate == null) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Client inconnu.");
            }
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
            customerRepository.save(customerToUpdate);
            return new CustomerDTO(customerToUpdate.getFirstName(), customerToUpdate.getLastName(), customerToUpdate.getEmail(), customerToUpdate.getPhone(), customerToUpdate.getAddress());
    }

    @Override
    public String deleteCustomer(String email) throws ResponseStatusException {
        Customer customerToDelete = customerRepository.findByEmail(email);
        if (customerToDelete != null) {
            customerRepository.delete(customerToDelete);
            return customerToDelete.getEmail();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Client inconnu.");
        }
    }

    // Tools

    @Override
    public Customer getOrCreateCustomer(Customer customer) {
        Customer customerInDB = customerRepository.findByEmail(customer.getEmail());
        if (customerInDB == null) {
            customerInDB = customerRepository.save(customer);
        }
        return customerInDB;
    }
}
