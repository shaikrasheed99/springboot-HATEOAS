package com.hateoas.link_builders;

import com.hateoas.products.controller.ProductController;
import com.hateoas.products.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductLinksBuilder implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {
        EntityModel<Product> productEntity = EntityModel.of(product);

        Link selfLink = linkTo(methodOn(ProductController.class).productById(product.getId())).withSelfRel();
        Link collectionLink = linkTo(methodOn(ProductController.class).products()).withRel(IanaLinkRelations.COLLECTION);

        productEntity.add(selfLink);
        productEntity.add(collectionLink);

        return productEntity;
    }
}
