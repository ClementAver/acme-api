package com.acme.api.services;

import com.acme.api.entities.OrderLine;
import com.acme.api.odt.OrderLineRequestBody;
import com.acme.api.repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderLineRepository orderRepository;

    public OrderLineService(OrderLineRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // take a look at this
    @Override
    public OrderLine getOrderLine(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderLine createOrderLine(OrderLineRequestBody orderRequestBody) {
        OrderLine order = new OrderLine();
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
}
