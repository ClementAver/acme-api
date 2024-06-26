package com.acme.api.controllers;

import com.acme.api.dto.OrderDTO;
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
    public Stream<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/order/{reference}")
    public OrderDTO getOrder(@PathVariable String reference) {
        try {
            return orderService.getOrderByReference(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO createOrder(@RequestBody OrderRequestBody orderRequestBody) {
        try {
            return orderService.createOrder(orderRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/order/{reference}", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO updateOrder(@PathVariable String reference, @RequestBody OrderRequestBody orderRequestBody) {
       try {
           return orderService.updateOrder(reference, orderRequestBody);
       } catch (ResponseStatusException e) {
           throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
       }
    }

    @DeleteMapping("/order/{reference}")
    public String deleteOrder(@PathVariable String reference) {
        try {
        return orderService.deleteOrder(reference);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

