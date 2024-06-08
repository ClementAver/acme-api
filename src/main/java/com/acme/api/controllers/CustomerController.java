package com.acme.api.controllers;

import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    // @Autowired if no constructor.
    final private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer")
    public Customer getCustomer(@RequestParam(name = "id", required=true) long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        return customerService.createCustomer(customerRequestBody);
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestParam(name = "id", required=true) long id) {
        customerService.deleteCustomer(id);
    }
}

