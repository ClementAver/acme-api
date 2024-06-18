package com.acme.api.controllers;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.entities.Customer;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api")
public class CustomerController {

    // @Autowired if no constructor.
    final private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public Stream<GetCustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

//    @GetMapping("/customer")
//    public Customer getCustomer(@RequestParam(name = "id", required=true) long id) {
//        Optional<Customer> customer = Optional.ofNullable(customerService.getCustomer(id));
//        return customer.orElse(null);
//    }

    @GetMapping("/customer")
    public GetCustomerDTO getCustomer(@RequestParam(name = "email", required=true) String email) {
        return customerService.getCustomerByEmail(email);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        return customerService.createCustomer(customerRequestBody);
    }

    @PutMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public Customer updateCustomer(@RequestParam(name = "id", required=true) long id, @RequestBody CustomerRequestBody customerRequestBody) {
        return customerService.updateCustomer(id, customerRequestBody);
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestParam(name = "id", required=true) long id) {
        customerService.deleteCustomer(id);
    }


}

