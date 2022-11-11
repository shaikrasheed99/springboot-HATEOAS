package com.hateoas.orders.model;

import com.hateoas.customers.model.Customer;
import com.hateoas.customers.model.CustomerRepository;
import com.hateoas.products.model.Product;
import com.hateoas.products.model.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    private Order ironmanOrdersIphone;
    private List<Order> orders;
    private Customer ironman;
    private Product iPhone;

    @BeforeEach
    void setUp() {
        ironman = new Customer("Ironman");
        Customer thor = new Customer("Thor");
        customerRepository.saveAll(Arrays.asList(ironman, thor));
        iPhone = new Product("iPhone", 80000);
        Product macBook = new Product("MacBook", 200000);
        productRepository.saveAll(Arrays.asList(iPhone, macBook));
        int quantity = 2;
        ironmanOrdersIphone = new Order(ironman, iPhone, quantity, quantity * iPhone.getPrice());
        Order ironmanOrdersMacBook = new Order(ironman, macBook, quantity, quantity * macBook.getPrice());
        Order thorOrdersIphone = new Order(thor, iPhone, quantity, quantity * iPhone.getPrice());
        Order thorOrdersMacBook = new Order(thor, macBook, quantity, quantity * macBook.getPrice());
        orders = Arrays.asList(ironmanOrdersIphone, ironmanOrdersMacBook, thorOrdersIphone, thorOrdersMacBook);
        orderRepository.saveAll(orders);
    }

    @Test
    void shouldBeAbleToSaveOrder() {
        Order savedOrder = orderRepository.save(ironmanOrdersIphone);

        assertEquals(savedOrder.getId(), ironmanOrdersIphone.getId());
        assertEquals(savedOrder.getCustomer().getId(), ironmanOrdersIphone.getCustomer().getId());
        assertEquals(savedOrder.getProduct().getId(), ironmanOrdersIphone.getProduct().getId());
        assertEquals(savedOrder.getCost(), ironmanOrdersIphone.getCost());
    }

    @Test
    void shouldBeAbleToGetOrderDetailsById() {
        Order orderById = orderRepository.findById(ironmanOrdersIphone.getId()).get();

        assertEquals(orderById.getCustomer().getName(), ironmanOrdersIphone.getCustomer().getName());
        assertEquals(orderById.getProduct().getId(), ironmanOrdersIphone.getProduct().getId());
        assertEquals(orderById.getProduct().getName(), ironmanOrdersIphone.getProduct().getName());
        assertEquals(orderById.getCost(), ironmanOrdersIphone.getCost());
    }

    @Test
    void shouldBeAbleToGetOrdersOfACustomer() {
        List<Order> customerOrders = orderRepository.findByCustomerId(ironman.getId());

        assertEquals(2, customerOrders.size());
        assertEquals("Ironman", customerOrders.get(0).getCustomer().getName());
        assertEquals("iPhone", customerOrders.get(0).getProduct().getName());
    }

    @Test
    void shouldBeAbleToGetOrdersOfAProduct() {
        List<Order> productOrders = orderRepository.findByProductId(iPhone.getId());

        assertEquals(2, productOrders.size());
        assertEquals("Ironman", productOrders.get(0).getCustomer().getName());
        assertEquals("Thor", productOrders.get(1).getCustomer().getName());
    }
}
