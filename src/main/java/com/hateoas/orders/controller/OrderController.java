package com.hateoas.orders.controller;

import com.hateoas.orders.model.Order;
import com.hateoas.orders.model.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<?> ordersOfCustomer(@PathVariable int customerId) {
        List<Order> ordersOfCustomer = orderRepository.findByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(ordersOfCustomer);
    }

    @GetMapping("/customers/{customerId}/orders/{id}")
    public ResponseEntity<?> orderOfCustomerById(@PathVariable int customerId, @PathVariable int id) {
        Order order = orderRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/products/{productId}/orders")
    public ResponseEntity<?> ordersOfProduct(@PathVariable int productId) {
        List<Order> ordersOfProduct = orderRepository.findByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ordersOfProduct);
    }

    @GetMapping("/products/{productId}/orders/{id}")
    public ResponseEntity<?> orderOfProductById(@PathVariable int productId, @PathVariable int id) {
        Order order = orderRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
