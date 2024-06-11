package com.acme.api.services;

import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
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
        order.setOrderLines(orderRequestBody.getOrderLines());
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

    @Override
    public Order updateOrder(Long id, OrderRequestBody orderRequestBody) {
        Order orderToUpdate = orderRepository.getReferenceById(id);
        if (orderRequestBody.getDate() != null) {
            orderToUpdate.setDate(orderRequestBody.getDate());
        }
        if (orderRequestBody.getIdCustomer() != null) {
            orderToUpdate.setIdCustomer(orderRequestBody.getIdCustomer());
        }
        return orderRepository.save(orderToUpdate);
    }
}
