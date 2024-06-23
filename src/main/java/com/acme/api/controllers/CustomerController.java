package com.acme.api.controllers;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.services.CustomerService;
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
    public Stream<GetCustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customer/{email}")
    public GetCustomerDTO getCustomer(@PathVariable String email) {
        try {
            return customerService.getCustomerByEmail(email);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public String createCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        try {
            customerService.createCustomer(customerRequestBody);
            return "Création effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/customer/{email}", consumes = APPLICATION_JSON_VALUE)
    public String updateCustomer(@PathVariable String email, @RequestBody CustomerRequestBody customerRequestBody) {
        try {
            customerService.updateCustomer(email, customerRequestBody);
            return "Mise à jour effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/customer/{email}")
    public String deleteCustomer(@PathVariable String email) {
        try {
            customerService.deleteCustomer(email);
            return "Supression effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

