package com.hateoas.link_builders;

import com.hateoas.customers.controller.CustomerController;
import com.hateoas.customers.model.Customer;
import com.hateoas.orders.controller.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerLinksBuilder implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        EntityModel<Customer> customerEntity = EntityModel.of(customer);

        Link selfLink = linkTo(methodOn(CustomerController.class).getById(customer.getId())).withSelfRel();
        Link collectionLink = linkTo(methodOn(CustomerController.class).customers()).withRel(IanaLinkRelations.COLLECTION);
        Link ordersLink = linkTo(methodOn(OrderController.class).ordersOfCustomer(customer.getId())).withRel("orders");

        customerEntity.add(selfLink);
        customerEntity.add(collectionLink);
        customerEntity.add(ordersLink);

        return customerEntity;
    }
}
