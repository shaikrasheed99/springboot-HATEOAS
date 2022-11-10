package com.hateoas.customers.controller;

import com.hateoas.customers.model.Customer;
import com.hateoas.customers.model.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<?> customers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }
}
