package com.acme.api.controllers;

import com.acme.api.dto.OrderDTO;
import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
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
    public OrderDTO getOrder(@RequestParam(value="reference") @Pattern(regexp="^ORD-\\d{13}$", message = "La référence de commande passée en paramètre de la requête n'est pas valide.") String reference) throws NotFoundException {
            return orderService.getOrderByReference(reference);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO createOrder(@Valid @RequestBody OrderRequestBody orderRequestBody) throws NotFoundException {
            return orderService.createOrder(orderRequestBody);
    }

    @PutMapping(value = "/order", consumes = APPLICATION_JSON_VALUE)
    public OrderDTO updateOrder(@RequestParam(value="reference") @Pattern(regexp="^ORD-\\d{13}$", message = "La référence de commande passée en paramètre de la requête n'est pas valide.") String reference, @Valid @RequestBody OrderRequestBody orderRequestBody) throws NotFoundException {
           return orderService.updateOrder(reference, orderRequestBody);
    }

    @DeleteMapping("/order")
    public String deleteOrder(@RequestParam(value="reference") @Pattern(regexp="^ORD-\\d{13}$", message = "La référence de commande passée en paramètre de la requête n'est pas valide.") String reference) throws NotFoundException {
        return orderService.deleteOrder(reference);
    }

    @GetMapping("/order/{reference}/order-lines")
    public Stream<OrderLineDTO> getOrderLinesFromOrder(@PathVariable @Pattern(regexp="^ORD-\\d{13}$", message = "La référence de commande constituante du chemin d'accès de la requête n'est pas valide.") String reference) throws NoMatchException, NotFoundException {
        return orderService.getOrderLinesFromOrder(reference);
    }
}

