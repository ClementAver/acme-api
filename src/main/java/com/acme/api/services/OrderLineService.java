package com.acme.api.services;

import com.acme.api.dto.GetAllCustomersDTO;
import com.acme.api.dto.GetAllOrderLinesDTO;
import com.acme.api.entities.OrderLine;
import com.acme.api.dto.OrderLineRequestBody;
import com.acme.api.mapper.GetAllCustomersDTOMapper;
import com.acme.api.mapper.GetAllOrderLinesDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderLineService implements OrderLineInterface{

    private final OrderLineRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final GetAllOrderLinesDTOMapper getAllOrderLinesDTOMapper;

    public OrderLineService(OrderLineRepository orderRepository, OrderLineRepository orderLineRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.getAllOrderLinesDTOMapper = new GetAllOrderLinesDTOMapper();
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

//    @Override
//    public List<OrderLine> getAllOrderLines() {
//        return orderRepository.findAll();
//    }

    @Override
    public Stream<GetAllOrderLinesDTO> getAllOrderLines() {
        return orderLineRepository.findAll()
                .stream().map(getAllOrderLinesDTOMapper);
    }

    @Override
    public void deleteOrderLine(long id) {
        orderRepository.deleteById(id);
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
