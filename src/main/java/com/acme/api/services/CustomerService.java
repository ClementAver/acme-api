package com.acme.api.services;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.dto.OrderDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.entities.Order;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.mappers.CustomerDTOMapper;
import com.acme.api.mappers.OrdersDTOMapper;
import com.acme.api.repositories.CustomerRepository;
import com.acme.api.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Validated
public class CustomerService implements CustomerInterface{

    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;
    private final OrderRepository orderRepository;
    private final OrdersDTOMapper ordersDTOMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDTOMapper customerDTOMapper, OrderRepository orderRepository, OrdersDTOMapper ordersDTOMapper) {
        this.customerRepository = customerRepository;
        this.customerDTOMapper = customerDTOMapper;
        this.orderRepository = orderRepository;
        this.ordersDTOMapper = ordersDTOMapper;
    }

    @Override
    public Stream<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream().map(customerDTOMapper);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) throws NotFoundException {
        Optional<Customer> customerInDB = customerRepository.findByEmail(email);
        if (customerInDB.isPresent()) {
            Customer customer = customerInDB.get();
            return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
        } else {
        throw new NotFoundException("Client non référencé.");
        }
    }

    @Override
    public Stream<OrderDTO> getOrdersFromCustomer(String email) throws NoMatchException {
        Set<Order> ordersInDB = orderRepository.findAllByIdCustomer_Email(email);
        if (ordersInDB.isEmpty()) {
            throw new NoMatchException("Aucune occurence.");
        }
        return ordersInDB.stream().map(ordersDTOMapper);
    }

    @Override
    public CustomerDTO createCustomer(CustomerRequestBody customerRequestBody) throws AlreadyExistException {
        Optional<Customer> customerInDB = customerRepository.findByEmail(customerRequestBody.getEmail());
        if (customerInDB.isPresent()) {
            throw new AlreadyExistException("Cet email a déjà été renseigné.");
        }

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

        customerRepository.save(customer);
        return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
    }

    @Override
    public CustomerDTO updateCustomer(String email, CustomerRequestBody customerRequestBody) throws NotFoundException {
        Optional<Customer> customerToUpdate = customerRepository.findByEmail(email);
        if (customerToUpdate.isPresent()) {
            Customer customer = customerToUpdate.get();
            if (customerRequestBody.getFirstName() != null) {
                customer.setFirstName(customerRequestBody.getFirstName());
            }
            if (customerRequestBody.getLastName() != null) {
                customer.setLastName(customerRequestBody.getLastName());
            }
            if (customerRequestBody.getEmail() != null) {
                customer.setEmail(customerRequestBody.getEmail());
            }
            if (customerRequestBody.getPhone() != null) {
                customer.setPhone(customerRequestBody.getPhone());
            }
            if (customerRequestBody.getAddress() != null) {
                customer.setAddress(customerRequestBody.getAddress());
            }
            customerRepository.save(customer);
            return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
        } else {
            throw new NotFoundException( "Client non référencé.");
        }
    }

    @Override
    public JsonObject deleteCustomer(String email) throws NotFoundException {
        Optional<Customer> customerToDelete = customerRepository.findByEmail(email);
        if (customerToDelete.isPresent()) {
            Customer customer = customerToDelete.get();
            customerRepository.delete(customer);
            return Json.createObjectBuilder()
                    .add("deleted", customer.getEmail())
                    .build();
        } else {
            throw new NotFoundException("Client non référencé.");
        }
    }

    // Tools

//    @Override
//    public Customer getOrCreateCustomer(Customer customer) {
//        Customer customerInDB = customerRepository.findByEmail(customer.getEmail());
//        if (customerInDB == null) {
//            customerInDB = customerRepository.save(customer);
//        }
//        return customerInDB;
//    }
}
