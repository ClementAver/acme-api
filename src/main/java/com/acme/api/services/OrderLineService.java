package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.Order;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.entities.Product;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderLineRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinesDTOMapper orderLinesDTOMapper;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderLineService(OrderLineRepository orderRepository, OrderLineRepository orderLineRepository, ProductService productService, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderService = orderService;
        this.orderLinesDTOMapper = new OrderLinesDTOMapper();
        this.productService = productService;
    }

    @Override
    public Stream<OrderLineDTO> getOrderLines() {
        return orderLineRepository.findAll()
                .stream().map(orderLinesDTOMapper);
    }

    @Override
    public OrderLineDTO getOrderLine(Long id) throws ResponseStatusException {
        Optional<OrderLine> orderLineInDB = orderLineRepository.findById(id);
        if (orderLineInDB.isPresent()) {
            OrderLine orderLine = orderLineInDB.get();
            return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
    }

    @Override
    public OrderLineDTO createOrderLine(OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException {
        OrderLine orderLine = new OrderLine();
        try {
            Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
            orderLine.setIdProduct(product);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        try {
            Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
            orderLine.setIdOrder(order);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        orderLine.setQuantity(orderLineRequestBody.getQuantity());
        orderRepository.save(orderLine);
        return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
    }

    @Override
    public OrderLineDTO updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException {
        Optional<OrderLine> orderLineToUpdate = orderLineRepository.findById(id);
        if (orderLineToUpdate.isPresent()) {
            OrderLine orderLine = orderLineToUpdate.get();
            if (orderLineRequestBody.getIdProduct() != null) {
                try {
                    Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
                    orderLine.setIdProduct(product);
                } catch (ResponseStatusException e) {
                    throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
                }
            }

            if (orderLineRequestBody.getIdOrder() != null) {
                try {
                    Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
                    orderLine.setIdOrder(order);
                } catch (ResponseStatusException e) {
                    throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
                }
            }

            if (orderLineRequestBody.getQuantity() != null) {
                orderLine.setQuantity(orderLineRequestBody.getQuantity());
            }
            orderLineRepository.save(orderLine);
            return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }
    }

    @Override
    public Long deleteOrderLine(Long id) throws ResponseStatusException {
        Optional<OrderLine> orderLineToDelete = orderLineRepository.findById(id);
        if (orderLineToDelete.isPresent()) {
            OrderLine orderLine = orderLineToDelete.get();
            orderLineRepository.delete(orderLine);
            return orderLine.getId();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }
    }
}
