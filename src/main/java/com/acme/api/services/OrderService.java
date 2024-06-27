package com.acme.api.services;

import com.acme.api.dto.OrderDTO;
import com.acme.api.dto.OrderLineDTO;
import com.acme.api.entities.Customer;
import com.acme.api.entities.Order;
import com.acme.api.dto.OrderRequestBody;
import com.acme.api.entities.OrderLine;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.mappers.OrdersDTOMapper;
import com.acme.api.repositories.CustomerRepository;
import com.acme.api.repositories.OrderLineRepository;
import com.acme.api.repositories.OrderRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.acme.api.entities.Order.generateReference;

@Service
public class OrderService implements OrderInterface{

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerService customerService;
    private final OrdersDTOMapper ordersDTOMapper;
    private final OrderLinesDTOMapper orderLinesDTOMapper;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, CustomerService customerService, OrdersDTOMapper ordersDTOMapper, OrderLinesDTOMapper orderLinesDTOMapper, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.customerService = customerService;
        this.ordersDTOMapper = ordersDTOMapper;
        this.orderLinesDTOMapper = orderLinesDTOMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public Stream<OrderDTO> getOrders() {
        return orderRepository.findAll()
                .stream().map(ordersDTOMapper);
    }

    @Override
    public Order getOrderEntity(String reference) throws ResponseStatusException {
        Optional<Order> orderInDB = orderRepository.findByReference(reference);
        if (orderInDB.isPresent()) {
            return orderInDB.get();
        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
    }

    @Override
    public OrderDTO getOrderByReference(String reference) throws ResponseStatusException {
        Optional<Order> orderInDB = orderRepository.findByReference(reference);
        if (orderInDB.isPresent()) {
            Order order = orderInDB.get();
        return new OrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
    }

    @Override
    public OrderDTO createOrder(OrderRequestBody orderRequestBody) throws ResponseStatusException {
        Order order = new Order();

        Optional<Customer> customerInDB = customerRepository.findByEmail(orderRequestBody.getCustomerEmail());
        if (customerInDB.isPresent()) {
            Customer customer = customerInDB.get();
            order.setIdCustomer(customer);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Client non référencé.");
        }

        if (orderRequestBody.getDate() == null) {
            order.setDate(LocalDate.now());
        } else {
            order.setDate(orderRequestBody.getDate());
        }

        order.setReference(generateReference());

        orderRepository.save(order);
        return new OrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
    }

    @Override
    public OrderDTO updateOrder(String reference, OrderRequestBody orderRequestBody) throws ResponseStatusException {
        Optional<Order> orderToUpdate = orderRepository.findByReference(reference);
        if (orderToUpdate.isPresent()) {
            Order order = orderToUpdate.get();

            if (orderRequestBody.getCustomerEmail() != null) {
                Optional<Customer> customerInDB = customerRepository.findByEmail(orderRequestBody.getCustomerEmail());
                if (customerInDB.isPresent()) {
                    Customer customer = customerInDB.get();
                    order.setIdCustomer(customer);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Client non référencé.");
                }
            }

            if (orderRequestBody.getDate() != null) {
                order.setDate(orderRequestBody.getDate());
            }
            orderRepository.save(order);
            return new OrderDTO(order.getReference(), order.getDate(), order.getIdCustomer().getEmail());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Commande non référencée.");
        }
    }

    @Override
    public String deleteOrder(String reference) throws ResponseStatusException {
        Optional<Order> orderToDelete = orderRepository.findByReference(reference);
        if (orderToDelete.isPresent()) {
            Order order = orderToDelete.get();
            orderRepository.delete(order);
            return order.getReference();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Ligne de facturation inconnu.");
        }
    }

    @Override
    public Stream<OrderLineDTO> getOrderLinesFromOrder(String orderReference) {
        Set<OrderLine> orderLinesInDB = orderLineRepository.findAllByIdOrder_Reference(orderReference);
        if (orderLinesInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        } else {
            return orderLinesInDB.stream().map(orderLinesDTOMapper);
        }
    }

    // Tools

//    @Override
//    public Order getOrCreateOrder(Order order) {
//        Order orderInDB = orderRepository.findByReference(order.getReference());
//        if (orderInDB == null) {
//            orderInDB = orderRepository.save(order);
//        }
//        return orderInDB;
//    }
}
