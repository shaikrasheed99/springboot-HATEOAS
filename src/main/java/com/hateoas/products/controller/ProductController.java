package com.hateoas.products.controller;

import com.hateoas.link_builders.ProductLinksBuilder;
import com.hateoas.products.model.Product;
import com.hateoas.products.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductLinksBuilder productLinksBuilder;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<?> products() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<EntityModel<Product>> productEntities = new ArrayList<>();

        products.forEach(product -> {
            EntityModel<Product> productEntity = productLinksBuilder.toModel(product);
            productEntities.add(productEntity);
        });

        CollectionModel<EntityModel<Product>> productCollection = CollectionModel.of(productEntities);
        Link selfLink = linkTo(methodOn(ProductController.class).products()).withSelfRel();
        productCollection.add(selfLink);

        return ResponseEntity.status(HttpStatus.OK).body(productCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> productById(@PathVariable int id) {
        Product product = productRepository.findById(id).get();

        EntityModel<Product> productEntity = productLinksBuilder.toModel(product);

        return ResponseEntity.status(HttpStatus.OK).body(productEntity);
    }
}
