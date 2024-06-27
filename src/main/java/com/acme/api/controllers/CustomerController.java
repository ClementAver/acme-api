package com.acme.api.controllers;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.dto.OrderDTO;
import com.acme.api.services.CustomerService;
import com.acme.api.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Stream<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customer")
    public CustomerDTO getCustomer(@Valid @PathParam(value="email") @Email String email) {
        try {
            return customerService.getCustomerByEmail(email);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerRequestBody customerRequestBody) {
        try {
            return customerService.createCustomer(customerRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public CustomerDTO updateCustomer(@Valid @PathParam(value="email") @Email String email, @Valid @RequestBody CustomerRequestBody customerRequestBody) {
        try {
            return customerService.updateCustomer(email, customerRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/customer")
    public String deleteCustomer(@Valid @PathParam(value="email") @Email String email) {
        try {
            return customerService.deleteCustomer(email);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/customer/{email}/orders")
    public Stream<OrderDTO> getOrdersFromCustomer(@Valid @PathVariable @Email String email) {
        return customerService.getOrdersFromCustomer(email);
    }
}