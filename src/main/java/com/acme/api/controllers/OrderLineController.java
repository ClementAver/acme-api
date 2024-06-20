package com.acme.api.controllers;

import com.acme.api.dto.GetOrderLineDTO;
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
    public Stream<GetOrderLineDTO> getOrderLines() {
        return orderLineService.getOrderLines();
    }

    @GetMapping("/order-lines-from-order")
    public Stream<GetOrderLineDTO> getOrderLinesFromOrder(@RequestParam(name = "reference", required=true) String orderReference) {
        return orderLineService.getOrderLinesFromOrder(orderReference);
    }

    @GetMapping("/order-lines-from-product")
    public Stream<GetOrderLineDTO> getOrderLinesFromProduct(@RequestParam(name = "reference", required=true) String productReference) {
        return orderLineService.getOrderLinesFromProduct(productReference);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public String createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            orderLineService.createOrderLine(orderLineRequestBody);
            return "Création effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public String updateOrderLine(@RequestParam(name = "id", required=true) long id, @RequestBody OrderLineRequestBody orderLineRequestBody) {
        try {
            orderLineService.updateOrderLine(id, orderLineRequestBody);
            return "Mise à jour effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/order-line")
    public String deleteOrderLine(@RequestParam(name = "id", required=true) long id) {
        try {
            orderLineService.deleteOrderLine(id);
            return "Supression effectuée.";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

