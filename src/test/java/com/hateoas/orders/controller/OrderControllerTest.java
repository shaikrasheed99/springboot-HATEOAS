package com.hateoas.orders.controller;

import com.hateoas.customers.model.Customer;
import com.hateoas.orders.model.Order;
import com.hateoas.orders.model.OrderRepository;
import com.hateoas.products.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;
    private Customer ironman;
    private List<Order> ordersOfIronman;

    @BeforeEach
    void setUp() {
        ironman = new Customer("Ironman");
        Customer thor = new Customer("Thor");
        Product iPhone = new Product("iPhone", 80000);
        Product macBook = new Product("MacBook", 200000);
        Order ironmanOrdersIphone = new Order(ironman, iPhone, 1, iPhone.getPrice());
        Order ironmanOrdersMacbook = new Order(ironman, macBook, 1, macBook.getPrice());
        Order thorOrdersMacbook = new Order(thor, macBook, 1, macBook.getPrice());
        Order thorOrdersIphone = new Order(thor, iPhone, 1, iPhone.getPrice());
        ordersOfIronman = Arrays.asList(ironmanOrdersIphone, ironmanOrdersMacbook);
    }

    @Test
    void shouldBeAbleReturnOrdersOfACustomer() throws Exception {
        when(orderRepository.findByCustomerId(ironman.getId())).thenReturn(ordersOfIronman);

        ResultActions result = mockMvc.perform(get("/customers/{id}/orders", ironman.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(orderRepository, times(1)).findByCustomerId(ironman.getId());
    }

    @Test
    void shouldBeAbleToReturnOrderOfACustomerByOrderId() throws Exception {
        Order order = ordersOfIronman.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));

        ResultActions result = mockMvc.perform(get("/customers/{id}/orders/{id}", ironman.getId(), order.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(orderRepository, times(1)).findById(order.getId());
    }
}
