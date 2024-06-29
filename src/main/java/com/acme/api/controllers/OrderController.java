package com.acme.api.controllers;

import com.acme.api.dto.OrderDTO;
import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/order")
    public OrderDTO getOrder(@Valid @PathParam(value="reference") String reference) throws NotFoundException {
            return orderService.getOrderByReference(reference);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO createOrder(@Valid @RequestBody OrderRequestBody orderRequestBody) throws NotFoundException {
            return orderService.createOrder(orderRequestBody);
    }

    @PutMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO updateOrder(@Valid @PathParam(value="reference") String reference, @Valid @RequestBody OrderRequestBody orderRequestBody) throws NotFoundException {
           return orderService.updateOrder(reference, orderRequestBody);
    }

    @DeleteMapping("/order")
    public String deleteOrder(@Valid @PathParam(value="reference") String reference) throws NotFoundException {
        return orderService.deleteOrder(reference);
    }

    @GetMapping("/order/{reference}/order-lines")
    public Stream<OrderLineDTO> getOrderLinesFromOrder(@Valid @PathVariable String reference) throws NoMatchException, NotFoundException {
        return orderService.getOrderLinesFromOrder(reference);
    }
}

