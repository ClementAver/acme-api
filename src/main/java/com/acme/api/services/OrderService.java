package com.acme.api.services;

import com.acme.api.entities.Order;
import com.acme.api.odt.OrderRequestBody;
import com.acme.api.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderInterface{

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(OrderRequestBody orderRequestBody) {
        Order order = new Order();
        order.setDate(orderRequestBody.getDate());
        order.setIdCustomer(orderRequestBody.getIdCustomer());
        order.setOrderlines(orderRequestBody.getOrderlines());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById((long) id);
    }
}
