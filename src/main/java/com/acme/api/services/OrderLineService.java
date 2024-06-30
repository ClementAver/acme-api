package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.Order;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.entities.Product;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import com.acme.api.repositories.OrderRepository;
import com.acme.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinesDTOMapper orderLinesDTOMapper;
    private final ProductRepository productRepository;

    public OrderLineService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderLinesDTOMapper = new OrderLinesDTOMapper();
        this.productRepository = productRepository;
    }

    @Override
    public Stream<OrderLineDTO> getOrderLines() {
        return orderLineRepository.findAll()
                .stream().map(orderLinesDTOMapper);
    }

    @Override
    public OrderLineDTO getOrderLine(Long id) throws NotFoundException {
        Optional<OrderLine> orderLineInDB = orderLineRepository.findById(id);
        if (orderLineInDB.isPresent()) {
            OrderLine orderLine = orderLineInDB.get();
            return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
        } else {
            throw new NotFoundException("Ligne de facturation non référencée.");
        }
    }

    @Override
    public OrderLineDTO createOrderLine(OrderLineRequestBody orderLineRequestBody) throws NotFoundException {
        OrderLine orderLine = new OrderLine();

        Optional<Product> productInDB = productRepository.findByReference(orderLineRequestBody.getProductReference());
        if (productInDB.isPresent()) {
            Product product = productInDB.get();
            orderLine.setIdProduct(product);
        } else {
            throw new NotFoundException("Produit non référencée.");
        }

        Optional<Order> orderInDB = orderRepository.findByReference(orderLineRequestBody.getOrderReference());
        if (orderInDB.isPresent()) {
            Order order = orderInDB.get();
            orderLine.setIdOrder(order);
        } else {
            throw new NotFoundException("Commande non référencée.");
        }

        orderLine.setQuantity(orderLineRequestBody.getQuantity());

        orderLineRepository.save(orderLine);
        return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
    }

    @Override
    public OrderLineDTO updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) throws NotFoundException {
        Optional<OrderLine> orderLineToUpdate = orderLineRepository.findById(id);
        if (orderLineToUpdate.isPresent()) {
            OrderLine orderLine = orderLineToUpdate.get();
            if (orderLineRequestBody.getProductReference() != null) {
                Optional<Product> productInDB = productRepository.findByReference(orderLineRequestBody.getProductReference());
                if (productInDB.isPresent()) {
                    Product product = productInDB.get();
                    orderLine.setIdProduct(product);
                } else {
                    throw new NotFoundException("Produit non référencée.");
                }
            }

            if (orderLineRequestBody.getOrderReference() != null) {
                Optional<Order> orderInDB = orderRepository.findByReference(orderLineRequestBody.getOrderReference());
                if (orderInDB.isPresent()) {
                    Order order = orderInDB.get();
                    orderLine.setIdOrder(order);
                } else {
                    throw new NotFoundException("Commande non référencée.");
                }
            }

            if (orderLineRequestBody.getQuantity() != null) {
                orderLine.setQuantity(orderLineRequestBody.getQuantity());
            }
            orderLineRepository.save(orderLine);
            return new OrderLineDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getProductReference(), orderLine.getOrderReference());
        } else {
            throw new NotFoundException("Ligne de facturation non référencée.");
        }
    }

    @Override
    public Long deleteOrderLine(Long id) throws NotFoundException {
        Optional<OrderLine> orderLineToDelete = orderLineRepository.findById(id);
        if (orderLineToDelete.isPresent()) {
            OrderLine orderLine = orderLineToDelete.get();
            orderLineRepository.delete(orderLine);
            return orderLine.getId();
        } else {
            throw new NotFoundException("Ligne de facturation non référencée.");
        }
    }
}
