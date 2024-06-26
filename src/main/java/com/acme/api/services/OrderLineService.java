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

import java.util.Set;
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
    public Stream<OrderLineDTO> getOrderLinesFromOrder(String orderReference) {
        Set<OrderLine> orderLinesInDB = orderLineRepository.findAllByIdOrder_Reference(orderReference);
        if (orderLinesInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        } else {
            return orderLinesInDB.stream().map(orderLinesDTOMapper);
        }
    }

    @Override
    public Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference) {
        Set<OrderLine> orderLineInDB = orderLineRepository.findAllByIdProduct_Reference(productReference);
        if (orderLineInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        }
        return orderLineInDB.stream().map(orderLinesDTOMapper);
    }

    @Override
    public void createOrderLine(OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException {
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
    }

    @Override
    public void updateOrderLine(long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException {
        OrderLine orderLineToUpdate = orderLineRepository.findById(id);
        if (orderLineToUpdate == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }

        if (orderLineRequestBody.getIdProduct() != null) {
            try {
                Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
                orderLineToUpdate.setIdProduct(product);
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
            }
        }

        if (orderLineRequestBody.getIdOrder() != null) {
            try {
                Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
                orderLineToUpdate.setIdOrder(order);
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
            }
        }

        if (orderLineRequestBody.getQuantity() != null) {
            orderLineToUpdate.setQuantity(orderLineRequestBody.getQuantity());
        }

        orderLineRepository.save(orderLineToUpdate);
    }

    @Override
    public void deleteOrderLine(long id) throws ResponseStatusException {
        OrderLine orderLineToDelete = orderLineRepository.findById(id);
        if (orderLineToDelete != null) {
            orderLineRepository.delete(orderLineToDelete);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }
    }
}
