package com.hateoas.customers.controller;

import com.hateoas.customers.model.Customer;
import com.hateoas.customers.model.CustomerRepository;
import com.hateoas.link_builders.CustomerLinksBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerLinksBuilder customerLinksBuilder;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<?> customers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        List<EntityModel<Customer>> customerEntities = new ArrayList<>();

        customers.forEach(customer -> {
            customerEntities.add(customerLinksBuilder.toModel(customer));
        });

        CollectionModel<EntityModel<Customer>> customerCollection = CollectionModel.of(customerEntities);
        Link selfLink = linkTo(methodOn(CustomerController.class).customers()).withSelfRel();
        customerCollection.add(selfLink);

        return ResponseEntity.status(HttpStatus.OK).body(customerCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id).get();

        EntityModel<Customer> customerEntity = customerLinksBuilder.toModel(customer);

        return ResponseEntity.status(HttpStatus.OK).body(customerEntity);
    }
}
