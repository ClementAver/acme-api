package com.acme.api.controllers;

import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order")
    public Order getOrder(@RequestParam(name = "id", required=true) long id) {
        return orderService.getOrder(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public Order createOrder(@RequestBody OrderRequestBody orderRequestBody) {
        return orderService.createOrder(orderRequestBody);
    }

    @DeleteMapping("/order")
    public void deleteOrder(@RequestParam(name = "id", required=true) long id) {
        orderService.deleteOrder(id);
    }
}

