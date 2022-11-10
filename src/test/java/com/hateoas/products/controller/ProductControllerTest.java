package com.hateoas.products.controller;

import com.hateoas.products.model.Product;
import com.hateoas.products.model.ProductRepository;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductRepository productRepository;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        Product iPhone = new Product("iPhone", 80000);
        Product macBook_pro = new Product("MacBook Pro", 200000);
        products = Arrays.asList(iPhone, macBook_pro);
    }

    @Test
    void shouldBeAbleToReturnProducts() throws Exception {
        when(productRepository.findAll()).thenReturn(products);

        ResultActions result = mockMvc.perform(get("/products"));

        result.andExpect(status().isOk()).andDo(print());

        verify(productRepository, times(1)).findAll();
    }
}
