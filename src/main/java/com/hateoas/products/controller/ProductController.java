package com.hateoas.products.controller;

import com.hateoas.products.model.Product;
import com.hateoas.products.model.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<?> products() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
