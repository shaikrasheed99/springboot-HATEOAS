package com.hateoas.products.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    private Product iPhone;

    @BeforeEach
    void setUp() {
        iPhone = new Product("iPhone 14", 80000);
        productRepository.save(iPhone);
    }

    @Test
    void shouldBeAbleToSaveProduct() {
        Product savedIphone = productRepository.save(iPhone);

        assertEquals(savedIphone.getId(), iPhone.getId());
        assertEquals(savedIphone.getName(), iPhone.getName());
    }

    @Test
    void shouldBeAbleToGetProductById() {
        Product iphoneById = productRepository.findById(iPhone.getId()).get();

        assertEquals(iphoneById.getId(), iphoneById.getId());
        assertEquals(iphoneById.getName(), iphoneById.getName());
        assertEquals(iphoneById.getPrice(), iphoneById.getPrice());
    }
}
