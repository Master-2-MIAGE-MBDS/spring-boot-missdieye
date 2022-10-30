package com.course.order.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class OrderUser {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> products;

    public OrderUser(Long id) {
        this.id = id;
    }

    public OrderUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void addProduct(OrderItem product) {
        this.products.add(product);
    }
}
