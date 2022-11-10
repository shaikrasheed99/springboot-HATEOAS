package com.hateoas.orders.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query(value = "SELECT * FROM orders WHERE customer_id = :id", nativeQuery = true)
    List<Order> findByCustomerId(@Param("id") int id);

    @Query(value = "SELECT * FROM orders WHERE product_id = :id", nativeQuery = true)
    List<Order> findByProductId(@Param("id") int id);
}
