package com.acme.api.controllers;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.services.CustomerService;
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
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer")
    public GetCustomerDTO getCustomer(@RequestParam(name = "email", required=true) String email) {
        return customerService.getCustomerByEmail(email);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public String createCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        try {
            customerService.createCustomer(customerRequestBody);
            return "Création effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public String updateCustomer(@RequestParam(name = "email", required=true) String email, @RequestBody CustomerRequestBody customerRequestBody) {
        try {
            customerService.updateCustomer(email, customerRequestBody);
            return "Mise à jour effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/customer")
    public String deleteCustomer(@RequestParam(name = "email", required=true) String email) {
        try {
            customerService.deleteCustomer(email);
            return "Supression effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

