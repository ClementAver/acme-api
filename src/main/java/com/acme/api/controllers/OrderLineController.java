package com.acme.api.controllers;

import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.entities.Employee;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.services.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class OrderLineController {

    // @Autowired if no constructor.
    final private OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping("/orderlines")
    public List<OrderLine> getOrderLines() {
        return orderLineService.getAllOrderLines();
    }

    @GetMapping("/orderline")
    public OrderLine getOrderLine(@RequestParam(name = "id", required=true) long id) {
        Optional<OrderLine> orderLine = Optional.ofNullable(orderLineService.getOrderLine(id));
        return orderLine.orElse(null);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/orderline", consumes = APPLICATION_JSON_VALUE)
    public OrderLine createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        return orderLineService.createOrderLine(orderLineRequestBody);
    }

    @DeleteMapping("/orderline")
    public void deleteOrderLine(@RequestParam(name = "id", required=true) long id) {
        orderLineService.deleteOrderLine(id);
    }

    @PutMapping(value = "/orderline", consumes = APPLICATION_JSON_VALUE)
    public OrderLine updateOrderLine(@RequestParam(name = "id", required=true) long id, @RequestBody OrderLineRequestBody orderLineRequestBody) {
        return orderLineService.updateOrderLine(id, orderLineRequestBody);
    }
}

