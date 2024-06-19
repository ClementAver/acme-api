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

    // take a look at this
    @Override
    public OrderLine getOrderLine(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderLine createOrderLine(OrderLineRequestBody orderLineRequestBody) {
        Product product = productService.getOrCreateProduct(productService.getProductEntity(orderLineRequestBody.getIdProductReference()));
        Order order = orderService.getOrCreateOrder(orderService.getOrderEntity(orderLineRequestBody.getIdOrderReference()));
        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(orderLineRequestBody.getQuantity());
        orderLine.setIdProduct(product);
        orderLine.setIdOrder(order);
        return orderRepository.save(orderLine);
    }

    @Override
    public Stream<GetOrderLineDTO> getAllOrderLines() {
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

    @Override
    public Stream<GetOrderLineDTO> getAllOrderLinesFromOrder(String orderReference) {
        return orderLineRepository.findAllByIdOrder_Reference(orderReference)
                .stream().map(getAllOrderLinesDTOMapper);
    }

    @Override
    public Stream<GetOrderLineDTO> getAllOrderLinesFromProduct(String productReference) {
        return orderLineRepository.findAllByIdProduct_Reference(productReference)
                .stream().map(getAllOrderLinesDTOMapper);
    }
}
