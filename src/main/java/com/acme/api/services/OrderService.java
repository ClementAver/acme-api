package com.acme.api.services;

import com.acme.api.dto.GetAllOrdersDTO;
import com.acme.api.entities.Customer;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.mapper.GetAllOrdersDTOMapper;
import com.acme.api.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static com.acme.api.entities.Order.generateDate;
import static com.acme.api.entities.Order.generateReference;

@Service
public class OrderService implements OrderInterface{

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final GetAllOrdersDTOMapper getAllOrdersDTOMapper;

    public OrderService(OrderRepository orderRepository, CustomerService customerService, GetAllOrdersDTOMapper getAllOrdersDTOMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.getAllOrdersDTOMapper = getAllOrdersDTOMapper;
    }

    @Override
    public Order createOrder(OrderRequestBody orderRequestBody) {
        Customer customer = customerService.getOrCreateCustomer(orderRequestBody.getIdCustomer());
        Order order = new Order();
        order.setReference(generateReference());
        order.setDate(generateDate());
        order.setIdCustomer(customer);
        if (orderRequestBody.getOrderLines() == null) {
            order.setOrderLines(new LinkedHashSet<>());
        } else {
            order.setOrderLines(orderRequestBody.getOrderLines());
        }
        return orderRepository.save(order);
    }

    @Override
    public Stream<GetAllOrdersDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream().map(getAllOrdersDTOMapper);
    }

    @Override
    public Stream<GetAllOrdersDTO> getAllOrdersFromCustomer(String customerEmail) {
        return orderRepository.findAllByIdCustomer_Email(customerEmail)
                .stream().map(getAllOrdersDTOMapper);
    }

    @Override
    public Order getOrder(String reference) {
        return orderRepository.findByReference(reference);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrder(Long id, OrderRequestBody orderRequestBody) {
        Order orderToUpdate = orderRepository.getReferenceById(id);
        if (orderRequestBody.getIdCustomer() != null) {
            orderToUpdate.setIdCustomer(orderRequestBody.getIdCustomer());
        }
        return orderRepository.save(orderToUpdate);
    }

    @Override
    public Order getOrCreateOrder(Order order) {
        Order orderInDB = orderRepository.findByReference(order.getReference());
        if (orderInDB == null) {
            orderInDB = orderRepository.save(order);
        }
        return orderInDB;
    }
}
