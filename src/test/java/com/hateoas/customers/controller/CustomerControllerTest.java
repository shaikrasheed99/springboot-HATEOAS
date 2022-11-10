package com.hateoas.customers.controller;

import com.hateoas.customers.model.Customer;
import com.hateoas.customers.model.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        Customer ironman = new Customer("Ironman");
        Customer thor = new Customer("Thor");
        Customer thanos = new Customer("Thanos");
        customers = new ArrayList<>();
        customers.add(ironman);
        customers.add(thor);
        customers.add(thanos);
    }

    @Test
    void shouldBeAbleToReturnCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(customers);

        ResultActions result = mockMvc.perform(get("/customers"));

        result.andExpect(status().isOk()).andDo(print());

        verify(customerRepository, times(1)).findAll();
    }
}
