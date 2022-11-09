package com.hateoas.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    private Customer ironman;

    @BeforeEach
    void setUp() {
        ironman = new Customer("Ironman");
        customerRepository.save(ironman);
    }

    @Test
    void shouldBeAbleToSaveCustomer() {
        Customer saved = customerRepository.save(ironman);

        assertEquals(saved.getName(), ironman.getName());
    }

    @Test
    void shouldBeAbleToReturnCustomerById() {
        Customer customer = customerRepository.findById(ironman.getId()).get();

        assertEquals(customer.getName(), ironman.getName());
    }
}
