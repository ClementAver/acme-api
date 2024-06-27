package com.acme.api.controllers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.services.OrderLineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class OrderLineController {

    // @Autowired if no constructor.
    final private OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping("/order-lines")
    public Stream<OrderLineDTO> getOrderLines() {
        return orderLineService.getOrderLines();
    }

    @GetMapping("/order-line/{id}")
    public OrderLineDTO getOrderLine(@Valid @PathVariable String id) {
        try {
            return orderLineService.getOrderLine(Long.parseLong(id));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public OrderLineDTO createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            return orderLineService.createOrderLine(orderLineRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/order-line/{id}", consumes = APPLICATION_JSON_VALUE)
    public OrderLineDTO updateOrderLine(@PathVariable Long id, @RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            return orderLineService.updateOrderLine(id, orderLineRequestBody);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/order-line/{id}")
    public Long deleteOrderLine(@PathVariable Long id) {
        try {
            return orderLineService.deleteOrderLine(id);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

