package com.acme.api.services;

import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderLineRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderRepository, OrderLineRepository orderLineRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
    }

    // take a look at this
    @Override
    public OrderLine getOrderLine(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderLine createOrderLine(OrderLineRequestBody orderRequestBody) {
        OrderLine order = new OrderLine();
        order.setQuantity(orderRequestBody.getQuantity());
        order.setIdProduct(orderRequestBody.getIdProduct());
        order.setIdOrder(orderRequestBody.getIdOrder());
        return orderRepository.save(order);
    }

    @Override
    public List<OrderLine> getAllOrderLines() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrderLine(long id) {
        orderRepository.deleteById((long) id);
    }

    @Override
    public OrderLine updateOrderLine(Long id, OrderLineRequestBody orderLineRequestBody) {
        OrderLine orderLineToUpdate = orderLineRepository.getReferenceById(id);
        if (orderLineRequestBody.getQuantity() != null) {
            orderLineToUpdate.setQuantity(orderLineRequestBody.getQuantity());
        }
        if (orderLineRequestBody.getIdProduct() != null) {
            orderLineToUpdate.setIdProduct(orderLineRequestBody.getIdProduct());
        }
        if (orderLineRequestBody.getIdOrder() != null) {
            orderLineToUpdate.setIdOrder(orderLineRequestBody.getIdOrder());
        }
        return orderLineRepository.save(orderLineToUpdate);
    }
}
