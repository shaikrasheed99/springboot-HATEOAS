package com.hateoas.orders.model;

import com.hateoas.customers.model.Customer;
import com.hateoas.products.model.Product;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
    private int quantity;
    private float cost;

    public Order() {
    }

    public Order(Customer customer, Product product, int quantity, float cost) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", cost=" + cost + ", quantity=" + quantity + ", customer=" + customer + ", product=" + product + '}';
    }
}
