package com.acme.api.controllers;

import com.acme.api.dto.GetOrderDTO;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class OrderController {

    // @Autowired if no constructor.
    final private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public Stream<GetOrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders-from-customer")
    public Stream<GetOrderDTO> getOrdersFromCustomer(@RequestParam(name = "email", required=true) String email) {
        return orderService.getOrdersFromCustomer(email);
    }

    @GetMapping("/order")
    public GetOrderDTO getOrder(@RequestParam(name = "reference", required=true) String reference) {
        try {
            return orderService.getOrderByReference(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public String createOrder(@RequestBody OrderRequestBody orderRequestBody) {
        try {
            orderService.createOrder(orderRequestBody);
            return "Création effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public String updateOrder(@RequestParam(name = "reference", required=true) String reference, @RequestBody OrderRequestBody orderRequestBody) {
       try {
           orderService.updateOrder(reference, orderRequestBody);
           return "Mise à jour effectuée.";
       } catch (ResponseStatusException e) {
           throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
       }
    }

    @DeleteMapping("/order")
    public String deleteOrder(@RequestParam(name = "reference", required=true) String reference) {
        try {
        orderService.deleteOrder(reference);
            return "Supression effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

