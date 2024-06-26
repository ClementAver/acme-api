package com.acme.api.controllers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.services.OrderLineService;
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

    @GetMapping("/order-lines-from-order/{orderReference}")
    public Stream<OrderLineDTO> getOrderLinesFromOrder(@PathVariable String orderReference) {
        return orderLineService.getOrderLinesFromOrder(orderReference);
    }

    @GetMapping("/order-lines-from-product/{productReference}")
    public Stream<OrderLineDTO> getOrderLinesFromProduct(@PathVariable String productReference) {
        return orderLineService.getOrderLinesFromProduct(productReference);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public String createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            orderLineService.createOrderLine(orderLineRequestBody);
            return "Création effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping(value = "/order-line/{id}", consumes = APPLICATION_JSON_VALUE)
    public String updateOrderLine(@PathVariable long id, @RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            orderLineService.updateOrderLine(id, orderLineRequestBody);
            return "Mise à jour effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @DeleteMapping("/order-line/{id}")
    public String deleteOrderLine(@PathVariable long id) {
        try {
            orderLineService.deleteOrderLine(id);
            return "Supression effectuée.";
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}

