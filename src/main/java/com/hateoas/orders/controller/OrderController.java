package com.hateoas.orders.controller;

import com.hateoas.link_builders.OrderLinksBuilder;
import com.hateoas.orders.model.Order;
import com.hateoas.orders.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    @Autowired
    private OrderLinksBuilder orderLinksBuilder;
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<?> ordersOfCustomer(@PathVariable int customerId) {
        List<Order> ordersOfCustomer = orderRepository.findByCustomerId(customerId);
        List<EntityModel<Order>> orderEntities = new ArrayList<>();

        ordersOfCustomer.forEach(order -> {
            orderEntities.add(orderLinksBuilder.toModel(order));
        });

        CollectionModel<EntityModel<Order>> orderCollectionEntity = CollectionModel.of(orderEntities);
        Link selfLink = linkTo(methodOn(OrderController.class).ordersOfCustomer(customerId)).withSelfRel();
        orderCollectionEntity.add(selfLink);

        return ResponseEntity.status(HttpStatus.OK).body(orderCollectionEntity);
    }

    @GetMapping("/customers/{customerId}/orders/{id}")
    public ResponseEntity<?> orderOfCustomerById(@PathVariable int customerId, @PathVariable int id) {
        Order order = orderRepository.findById(id).get();

        EntityModel<Order> orderEntity = orderLinksBuilder.toModel(order);

        return ResponseEntity.status(HttpStatus.OK).body(orderEntity);
    }

    @GetMapping("/products/{productId}/orders")
    public ResponseEntity<?> ordersOfProduct(@PathVariable int productId) {
        List<Order> ordersOfProduct = orderRepository.findByProductId(productId);
        List<EntityModel<Order>> orderEntities = new ArrayList<>();

        ordersOfProduct.forEach(order -> {
            orderEntities.add(orderLinksBuilder.toModel(order));
        });

        CollectionModel<EntityModel<Order>> collectionEntity = CollectionModel.of(orderEntities);
        Link selfLink = linkTo(methodOn(OrderController.class).ordersOfProduct(productId)).withSelfRel();
        collectionEntity.add(selfLink);

        return ResponseEntity.status(HttpStatus.OK).body(collectionEntity);
    }

    @GetMapping("/products/{productId}/orders/{id}")
    public ResponseEntity<?> orderOfProductById(@PathVariable int productId, @PathVariable int id) {
        Order order = orderRepository.findById(id).get();

        EntityModel<Order> orderEntityModel = orderLinksBuilder.toModel(order);

        return ResponseEntity.status(HttpStatus.OK).body(orderEntityModel);
    }
}
