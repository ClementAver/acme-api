package com.acme.api.services;

import com.acme.api.dto.GetOrderLineDTO;
import com.acme.api.entities.Order;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.entities.Product;
import com.acme.api.mapper.GetAllOrderLinesDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderLineRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final GetAllOrderLinesDTOMapper getAllOrderLinesDTOMapper;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderLineService(OrderLineRepository orderRepository, OrderLineRepository orderLineRepository, ProductService productService, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderService = orderService;
        this.getAllOrderLinesDTOMapper = new GetAllOrderLinesDTOMapper();
        this.productService = productService;
    }

    @Override
    public Stream<GetOrderLineDTO> getOrderLines() {
        return orderLineRepository.findAll()
                .stream().map(getAllOrderLinesDTOMapper);
    }

    @Override
    public Stream<GetOrderLineDTO> getOrderLinesFromOrder(String orderReference) {
        return orderLineRepository.findAllByIdOrder_Reference(orderReference)
                .stream().map(getAllOrderLinesDTOMapper);
    }

    @Override
    public Stream<GetOrderLineDTO> getOrderLinesFromProduct(String productReference) {
        return orderLineRepository.findAllByIdProduct_Reference(productReference)
                .stream().map(getAllOrderLinesDTOMapper);
    }

    @Override
    public void createOrderLine(OrderLineRequestBody orderLineRequestBody) throws Exception {
        OrderLine orderLine = new OrderLine();
        try {
            Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
            orderLine.setIdProduct(product);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
            orderLine.setIdOrder(order);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        orderLine.setQuantity(orderLineRequestBody.getQuantity());
        orderRepository.save(orderLine);
    }

    @Override
    public void updateOrderLine(long id, OrderLineRequestBody orderLineRequestBody) throws Exception {
        OrderLine orderLineToUpdate = orderLineRepository.findById(id);
        if (orderLineToUpdate == null) {
            throw new Exception("Ligne de facturation inconnu.");
        }
        try {
            Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
            orderLineToUpdate.setIdProduct(product);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
            orderLineToUpdate.setIdOrder(order);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (orderLineRequestBody.getQuantity() != null) {
            orderLineToUpdate.setQuantity(orderLineRequestBody.getQuantity());
        }

        orderLineRepository.save(orderLineToUpdate);
    }

    @Override
    public void deleteOrderLine(long id) throws Exception {
        OrderLine orderLineToDelete = orderLineRepository.findById(id);
        if (orderLineToDelete != null) {
            orderLineRepository.delete(orderLineToDelete);
        } else {
            throw new Exception("Ligne de facturation inconnu.");
        }
    }
}
