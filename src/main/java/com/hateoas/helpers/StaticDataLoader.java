package com.hateoas.helpers;

import com.hateoas.customers.model.Customer;
import com.hateoas.customers.model.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class StaticDataLoader {
    private final CustomerRepository customerRepository;

    public StaticDataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            Customer ironman = new Customer("Ironman");
            Customer thor = new Customer("Thor");
            Customer thanos = new Customer("Thanos");

            customerRepository.saveAll(Arrays.asList(ironman, thor, thanos));
        };
    }
}
