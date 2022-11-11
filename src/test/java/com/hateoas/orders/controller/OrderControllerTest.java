package com.hateoas.orders.controller;

import com.hateoas.customers.model.Customer;
import com.hateoas.link_builders.OrderLinksBuilder;
import com.hateoas.orders.model.Order;
import com.hateoas.orders.model.OrderRepository;
import com.hateoas.products.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderLinksBuilder orderLinksBuilder;
    @MockBean
    private OrderRepository orderRepository;
    private Customer ironman;
    private List<Order> ordersOfIronman;
    private Product iPhone;
    private List<Order> ordersOfIphone;

    @BeforeEach
    void setUp() {
        ironman = new Customer("Ironman");
        Customer thor = new Customer("Thor");
        iPhone = new Product("iPhone", 80000);
        Product macBook = new Product("MacBook", 200000);
        Order ironmanOrdersIphone = new Order(ironman, iPhone, 1, iPhone.getPrice());
        Order ironmanOrdersMacbook = new Order(ironman, macBook, 1, macBook.getPrice());
        Order thorOrdersMacbook = new Order(thor, macBook, 1, macBook.getPrice());
        Order thorOrdersIphone = new Order(thor, iPhone, 1, iPhone.getPrice());
        ordersOfIronman = Arrays.asList(ironmanOrdersIphone, ironmanOrdersMacbook);
        ordersOfIphone = Arrays.asList(ironmanOrdersIphone, thorOrdersIphone);
    }

    @Test
    void shouldBeAbleReturnOrdersOfACustomer() throws Exception {
        when(orderRepository.findByCustomerId(ironman.getId())).thenReturn(ordersOfIronman);

        ResultActions result = mockMvc.perform(get("/customers/{id}/orders", ironman.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(orderRepository, times(1)).findByCustomerId(ironman.getId());
    }

    @Test
    void shouldBeAbleReturnOrdersOfACustomerWithHATEOASLinks() throws Exception {
        when(orderRepository.findByCustomerId(ironman.getId())).thenReturn(ordersOfIronman);
        Order order = ordersOfIronman.get(0);
        EntityModel<Order> orderEntityModel = orderLinksBuilder.toModel(order);
        List<Link> orderSelfLinks = orderEntityModel.getLinks("self");
        Link customerLink = orderEntityModel.getRequiredLink("customer");
        Link productLink = orderEntityModel.getRequiredLink("product");

        ResultActions result = mockMvc.perform(get("/customers/{id}/orders", ironman.getId()));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orderList[0].id").value(order.getId()))
                .andExpect(jsonPath("$._embedded.orderList[0].customer.name").value(order.getCustomer().getName()))
                .andExpect(jsonPath("$._embedded.orderList[0].product.id").value(order.getProduct().getId()))
                .andExpect(jsonPath("$._embedded.orderList[0]._links.self[0].href").value(orderSelfLinks.get(0).getHref()))
                .andExpect(jsonPath("$._embedded.orderList[0]._links.self[1].href").value(orderSelfLinks.get(1).getHref()))
                .andExpect(jsonPath("$._embedded.orderList[0]._links.customer.href").value(customerLink.getHref()))
                .andExpect(jsonPath("$._embedded.orderList[0]._links.product.href").value(productLink.getHref()))
                .andExpect(jsonPath("$._links.self.href").value(customerLink.getHref()))
                .andDo(print());

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

    @Test
    void shouldBeAbleReturnOrdersOfAProduct() throws Exception {
        when(orderRepository.findByProductId(iPhone.getId())).thenReturn(ordersOfIphone);

        ResultActions result = mockMvc.perform(get("/products/{id}/orders", iPhone.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(orderRepository, times(1)).findByProductId(iPhone.getId());
    }

    @Test
    void shouldBeAbleToReturnOrderOfAProductByOrderId() throws Exception {
        Order order = ordersOfIphone.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));

        ResultActions result = mockMvc.perform(get("/products/{id}/orders/{id}", iPhone.getId(), order.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(orderRepository, times(1)).findById(order.getId());
    }
}
