package com.acme.api.controllers;

import com.acme.api.dto.CustomerDTO;
import com.acme.api.dto.CustomerRequestBody;
import com.acme.api.dto.OrderDTO;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@Validated
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
    public CustomerDTO getCustomer(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email) throws NotFoundException {
        return customerService.getCustomerByEmail(email);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerRequestBody customerRequestBody) throws AlreadyExistException {
            return customerService.createCustomer(customerRequestBody);
    }

    @PutMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public CustomerDTO updateCustomer(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email, @Valid @RequestBody CustomerRequestBody customerRequestBody) throws NotFoundException {
            return customerService.updateCustomer(email, customerRequestBody);
    }

    @DeleteMapping("/customer")
    public String deleteCustomer(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email) throws NotFoundException {
            return customerService.deleteCustomer(email);
    }

    @GetMapping("/customer/{email}/orders")
    public Stream<OrderDTO> getOrdersFromCustomer(@Valid @PathVariable @Email(message = "L'adresse email constituante du chemin d'accès de la requête est invalide.") String email) throws NoMatchException {
        return customerService.getOrdersFromCustomer(email);
    }
}