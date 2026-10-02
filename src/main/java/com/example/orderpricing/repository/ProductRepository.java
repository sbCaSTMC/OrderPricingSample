package com.example.orderpricing.repository;

import com.example.orderpricing.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
