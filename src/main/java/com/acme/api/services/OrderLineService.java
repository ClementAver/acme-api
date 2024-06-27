package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.Order;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.entities.Product;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import com.acme.api.repositories.OrderRepository;
import com.acme.api.repositories.ProductRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinesDTOMapper orderLinesDTOMapper;
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductRepository productRepository;

    public OrderLineService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductService productService, OrderService orderService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderService = orderService;
        this.orderLinesDTOMapper = new OrderLinesDTOMapper();
        this.productService = productService;
        this.productRepository = productRepository;
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

        Optional<Product> productInDB = productRepository.findByReference(orderLineRequestBody.getProductReference());
        if (productInDB.isPresent()) {
            Product product = productInDB.get();
            orderLine.setIdProduct(product);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencée.");
        }

        Optional<Order> orderInDB = orderRepository.findByReference(orderLineRequestBody.getOrderReference());
        if (orderInDB.isPresent()) {
            Order order = orderInDB.get();
            orderLine.setIdOrder(order);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }

        orderLine.setQuantity(orderLineRequestBody.getQuantity());

        orderLineRepository.save(orderLine);
        return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
    }

    @Override
    public OrderLineDTO updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) throws ResponseStatusException {
        Optional<OrderLine> orderLineToUpdate = orderLineRepository.findById(id);
        if (orderLineToUpdate.isPresent()) {
            OrderLine orderLine = orderLineToUpdate.get();
            if (orderLineRequestBody.getProductReference() != null) {
                Optional<Product> productInDB = productRepository.findByReference(orderLineRequestBody.getProductReference());
                if (productInDB.isPresent()) {
                    Product product = productInDB.get();
                    orderLine.setIdProduct(product);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencée.");
                }
            }

            if (orderLineRequestBody.getOrderReference() != null) {
                Optional<Order> orderInDB = orderRepository.findByReference(orderLineRequestBody.getOrderReference());
                if (orderInDB.isPresent()) {
                    Order order = orderInDB.get();
                    orderLine.setIdOrder(order);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
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
