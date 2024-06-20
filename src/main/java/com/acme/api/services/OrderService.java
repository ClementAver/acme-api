package com.acme.api.services;

import com.acme.api.dto.GetOrderDTO;
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
    public Stream<GetOrderDTO> getOrders() {
        return orderRepository.findAll()
                .stream().map(getAllOrdersDTOMapper);
    }

    @Override
    public Stream<GetOrderDTO> getOrdersFromCustomer(String email) {
        return orderRepository.findAllByIdCustomer_Email(email)
                .stream().map(getAllOrdersDTOMapper);
    }

    @Override
    public Order getOrderEntity(String reference) throws Exception {
        Order orderInDB = orderRepository.findByReference(reference);
        if (orderInDB == null) {
            throw new Exception("Commande non référencée");
        }
        return orderInDB;
    }

    @Override
    public GetOrderDTO getOrderByReference(String reference) throws Exception {
        Order orderInDB = orderRepository.findByReference(reference);
        if (orderInDB == null) {
            throw new Exception("Commande non référencée.");
        }
        return new GetOrderDTO(orderInDB.getReference(), orderInDB.getDate(), orderInDB.getIdCustomer().getEmail());
    }

    @Override
    public void createOrder(OrderRequestBody orderRequestBody) throws Exception {
        Order order = new Order();
        try {
            Customer customer = customerService.getOrCreateCustomer(orderRequestBody.getIdCustomer());
            order.setIdCustomer(customer);
        } catch (Exception e) {
            throw new Exception("Données invalides ou incomplètes.");
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
    }

    @Override
    public void updateOrder(String reference, OrderRequestBody orderRequestBody) throws Exception {
        Order orderToUpdate = orderRepository.findByReference(reference);
        if (orderToUpdate == null) {
            throw new Exception("Commande non référencé.");
        }
        if (orderRequestBody.getIdCustomer() != null) {
            try {
                Customer customer = customerService.getOrCreateCustomer(orderRequestBody.getIdCustomer());
                orderToUpdate.setIdCustomer(customer);
            } catch (Exception e) {
                throw new Exception("Données saisies invalides ou incomplètes.");
            }
        }
        if (orderRequestBody.getDate() != null) {
            orderToUpdate.setDate(orderRequestBody.getDate());
        }
        orderRepository.save(orderToUpdate);
    }

    @Override
    public void deleteOrder(String reference) throws Exception {
        Order orderToDelete = orderRepository.findByReference(reference);
        if (orderToDelete != null) {
            orderRepository.delete(orderToDelete);
        } else {
            throw new Exception("Commande non référencé.");
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
