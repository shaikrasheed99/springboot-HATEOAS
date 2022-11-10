package com.hateoas.products.controller;

import com.hateoas.link_builders.ProductLinksBuilder;
import com.hateoas.products.model.Product;
import com.hateoas.products.model.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductLinksBuilder productLinksBuilder;
    @MockBean
    private ProductRepository productRepository;
    private List<Product> products;
    private Product iPhone;

    @BeforeEach
    void setUp() {
        iPhone = new Product("iPhone", 80000);
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

    @Test
    void shouldBeAbleToReturnProductsWithHATEOASLinks() throws Exception {
        when(productRepository.findAll()).thenReturn(products);
        EntityModel<Product> productEntityModel = productLinksBuilder.toModel(iPhone);
        Link productSelfLink = productEntityModel.getRequiredLink(IanaLinkRelations.SELF);
        Link productCollectionLink = productEntityModel.getRequiredLink(IanaLinkRelations.COLLECTION);
        Link productsLink = linkTo(methodOn(ProductController.class).products()).withSelfRel();

        ResultActions result = mockMvc.perform(get("/products"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productList[0].name").value(products.get(0).getName()))
                .andExpect(jsonPath("$._embedded.productList[1].id").value(products.get(1).getId()))
                .andExpect(jsonPath("$._embedded.productList[0]._links.self.href").value(productSelfLink.getHref()))
                .andExpect(jsonPath("$._embedded.productList[0]._links.collection.href").value(productCollectionLink.getHref()))
                .andExpect(jsonPath("$._links.self.href").value(productsLink.getHref()))
                .andDo(print());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldBeAbleReturnProductById() throws Exception {
        when(productRepository.findById(iPhone.getId())).thenReturn(Optional.ofNullable(iPhone));

        ResultActions result = mockMvc.perform(get("/products/{id}", iPhone.getId()));

        result.andExpect(status().isOk()).andDo(print());

        verify(productRepository, times(1)).findById(iPhone.getId());
    }
}
