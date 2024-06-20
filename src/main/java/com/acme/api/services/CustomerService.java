package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.mapper.GetCustomerDTOMapper;
import com.acme.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class CustomerService implements CustomerInterface{

    private final CustomerRepository customerRepository;
    private final GetCustomerDTOMapper getCustomerDTOMapper;

    public CustomerService(CustomerRepository customerRepository, GetCustomerDTOMapper getCustomerDTOMapper) {
        this.customerRepository = customerRepository;
        this.getCustomerDTOMapper = getCustomerDTOMapper;
    }

    @Override
    public Stream<GetCustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream().map(getCustomerDTOMapper);
    }

    @Override
    public GetCustomerDTO getCustomerByEmail(String email) throws Exception {
        Customer customerInDB = customerRepository.findByEmail(email);
        if (customerInDB == null) {
            throw new Exception("Email inconnu.");
        }
        return new GetCustomerDTO(customerInDB.getFirstName(), customerInDB.getLastName(), customerInDB.getEmail(), customerInDB.getPhone(), customerInDB.getAddress());
    }

    @Override
    public void createCustomer(CustomerRequestBody customerRequestBody) throws Exception {
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
            throw new Exception("Cet email a déjà été renseigné.");
        }
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(String email, CustomerRequestBody customerRequestBody) throws Exception {
            Customer customerToUpdate = customerRepository.findByEmail(email);
            if (customerToUpdate == null) {
                throw new Exception("Client inconnu.");
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
    }

    @Override
    public void deleteCustomer(String email) throws Exception {
        Customer customerToDelete = customerRepository.findByEmail(email);
        if (customerToDelete != null) {
            customerRepository.delete(customerToDelete);
        } else {
            throw new Exception("Client inconnu.");
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
