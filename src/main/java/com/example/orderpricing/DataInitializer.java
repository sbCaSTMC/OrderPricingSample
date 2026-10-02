package com.example.orderpricing;

import com.example.orderpricing.domain.Customer;
import com.example.orderpricing.domain.CustomerRank;
import com.example.orderpricing.domain.Product;
import com.example.orderpricing.repository.CustomerRepository;
import com.example.orderpricing.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initialData(CustomerRepository customers, ProductRepository products) {
        return args -> {
            customers.saveAll(List.of(
                    new Customer("C001", CustomerRank.REGULAR),
                    new Customer("C002", CustomerRank.GOLD)));
            products.saveAll(List.of(
                    new Product("P001", "Standard Keyboard", 10000, 0),
                    new Product("P002", "Premium Mouse", 5000, 20),
                    new Product("P003", "USB-C Dock", 15000, 0)));
        };
    }
}
