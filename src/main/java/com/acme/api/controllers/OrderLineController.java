package com.acme.api.controllers;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.OrderLineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
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
    public OrderLineDTO getOrderLine(@PathVariable @Min(value = 1, message = "L'identifiant constituant du chemin d'accès de la requête n'est pas valide.") Long id) throws NotFoundException {
            return orderLineService.getOrderLine(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order-line", consumes = APPLICATION_JSON_VALUE)
    public OrderLineDTO createOrderLine(@Valid @RequestBody OrderLineRequestBody orderLineRequestBody) throws NotFoundException {
            return orderLineService.createOrderLine(orderLineRequestBody);
    }

    @PutMapping(value = "/order-line/{id}", consumes = APPLICATION_JSON_VALUE)
    public OrderLineDTO updateOrderLine(@PathVariable @Min(value = 1, message = "L'identifiant constituant du chemin d'accès de la requête n'est pas valide.") Long id, @Valid @RequestBody OrderLineRequestBody orderLineRequestBody) throws NotFoundException {
            return orderLineService.updateOrderLine(id, orderLineRequestBody);
    }

    @DeleteMapping("/order-line/{id}")
    public Long deleteOrderLine(@PathVariable @Min(value = 1, message = "L'identifiant constituant du chemin d'accès de la requête n'est pas valide.") Long id) throws NotFoundException {
            return orderLineService.deleteOrderLine(id);
    }
}

