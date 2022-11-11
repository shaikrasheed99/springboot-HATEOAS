package com.hateoas.link_builders;

import com.hateoas.customers.model.Customer;
import com.hateoas.orders.controller.OrderController;
import com.hateoas.orders.model.Order;
import com.hateoas.products.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinksBuilder implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderEntity = EntityModel.of(order);
        Customer customer = order.getCustomer();
        Product product = order.getProduct();

        orderEntity.add(linkTo(methodOn(OrderController.class).orderOfCustomerById(customer.getId(), order.getId())).withSelfRel());
        orderEntity.add(linkTo(methodOn(OrderController.class).orderOfProductById(product.getId(), order.getId())).withSelfRel());
        orderEntity.add(linkTo(methodOn(OrderController.class).ordersOfCustomer(customer.getId())).withRel("customer"));
        orderEntity.add(linkTo(methodOn(OrderController.class).ordersOfProduct(product.getId())).withRel("product"));

        return orderEntity;
    }
}
