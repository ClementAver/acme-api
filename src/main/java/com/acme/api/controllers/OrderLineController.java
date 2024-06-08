package com.acme.api.controllers;

import com.acme.api.entities.OrderLine;
import com.acme.api.odt.OrderLineRequestBody;
import com.acme.api.services.OrderLineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return orderLineService.getOrderLine(id);
    }

    @PostMapping("/orderline")
    public OrderLine createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        return orderLineService.createOrderLine(orderLineRequestBody);
    }

    @DeleteMapping("/orderline")
    public void deleteOrderLine(@RequestParam(name = "id", required=true) long id) {
        orderLineService.deleteOrderLine(id);
    }
}

