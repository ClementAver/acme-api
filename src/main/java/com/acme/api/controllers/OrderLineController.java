package com.acme.api.controllers;

import com.acme.api.dto.GetOrderLineDTO;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.services.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        return orderLineService.getAllOrderLines();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public OrderLine createOrderLine(@RequestBody OrderLineRequestBody orderLineRequestBody) {
        return orderLineService.createOrderLine(orderLineRequestBody);
    }

    @PutMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public OrderLine updateOrderLine(@RequestParam(name = "id", required=true) long id, @RequestBody OrderLineRequestBody orderLineRequestBody) {
        return orderLineService.updateOrderLine(id, orderLineRequestBody);
    }

    @DeleteMapping("/order-line")
    public void deleteOrderLine(@RequestParam(name = "id", required=true) long id) {
        orderLineService.deleteOrderLine(id);
    }

    @GetMapping("/order-lines-from-order")
    public Stream<GetOrderLineDTO> getOrderLinesFromOrder(@RequestParam(name = "reference", required=true) String orderReference) {
        return orderLineService.getAllOrderLinesFromOrder(orderReference);
    }

    @GetMapping("/order-lines-from-product")
    public Stream<GetOrderLineDTO> getOrderLinesFromProduct(@RequestParam(name = "reference", required=true) String productReference) {
        return orderLineService.getAllOrderLinesFromProduct(productReference);
    }

}

