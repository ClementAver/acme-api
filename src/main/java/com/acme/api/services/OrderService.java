package com.acme.api.services;

import com.acme.api.dto.OrderDTO;
import com.acme.api.entities.Customer;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.mappers.OrdersDTOMapper;
import com.acme.api.repositories.OrderRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.acme.api.entities.Order.generateDate;
import static com.acme.api.entities.Order.generateReference;
import static java.util.Arrays.stream;

@Service
public class OrderService implements OrderInterface{

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrdersDTOMapper ordersDTOMapper;

    public OrderService(OrderRepository orderRepository, CustomerService customerService, OrdersDTOMapper ordersDTOMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.ordersDTOMapper = ordersDTOMapper;
    }

    @Override
    public Stream<OrderDTO> getOrders() {
        return orderRepository.findAll()
                .stream().map(ordersDTOMapper);
    }

    @Override
    public Stream<OrderDTO> getOrdersFromCustomer(String email) {
        Set<Order> ordersInDB = orderRepository.findAllByIdCustomer_Email(email);
        if (ordersInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        }
        return ordersInDB.stream().map(ordersDTOMapper);
    }

    @Override
    public Order getOrderEntity(String reference) throws ResponseStatusException {
        Order orderInDB = orderRepository.findByReference(reference);
        if (orderInDB == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
        return orderInDB;
    }

    @Override
    public OrderDTO getOrderByReference(String reference) throws ResponseStatusException {
        Order orderInDB = orderRepository.findByReference(reference);
        if (orderInDB == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
        return new OrderDTO(orderInDB.getReference(), orderInDB.getDate(), orderInDB.getIdCustomer().getEmail());
    }

    @Override
    public OrderDTO createOrder(OrderRequestBody orderRequestBody) throws ResponseStatusException {
        Order order = new Order();
        try {
            Customer customer = customerService.getOrCreateCustomer(orderRequestBody.getIdCustomer());
            order.setIdCustomer(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Données invalides ou incomplètes.");
        }

        if (orderRequestBody.getDate() == null) {
            order.setDate(generateDate());
        } else {
            order.setDate(orderRequestBody.getDate());
        }
        if (orderRequestBody.getOrderLines() == null) {
            order.setOrderLines(new LinkedHashSet<>());
        } else {
            order.setOrderLines(orderRequestBody.getOrderLines());
        }

        order.setReference(generateReference());

        orderRepository.save(order);
        return new OrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
    }

    @Override
    public OrderDTO updateOrder(String reference, OrderRequestBody orderRequestBody) throws ResponseStatusException {
        Order orderToUpdate = orderRepository.findByReference(reference);
        if (orderToUpdate == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
        if (orderRequestBody.getIdCustomer() != null) {
            try {
                Customer customer = customerService.getOrCreateCustomer(orderRequestBody.getIdCustomer());
                orderToUpdate.setIdCustomer(customer);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Données saisies invalides ou incomplètes.");
            }
        }
        if (orderRequestBody.getDate() != null) {
            orderToUpdate.setDate(orderRequestBody.getDate());
        }
        orderRepository.save(orderToUpdate);
        return new OrderDTO(orderToUpdate.getReference(), orderToUpdate.getDate(), orderToUpdate.getIdCustomer().getEmail());
    }

    @Override
    public String deleteOrder(String reference) throws ResponseStatusException {
        Order orderToDelete = orderRepository.findByReference(reference);
        if (orderToDelete != null) {
            orderRepository.delete(orderToDelete);
            return orderToDelete.getReference();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }
    }

    // Tools

    @Override
    public Order getOrCreateOrder(Order order) {
        Order orderInDB = orderRepository.findByReference(order.getReference());
        if (orderInDB == null) {
            orderInDB = orderRepository.save(order);
        }
        return orderInDB;
    }
}
