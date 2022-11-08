package com.course.cart.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> products;

    public Cart(Long id) {
        this.id = id;
    }

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public void addProduct(CartItem product) {
        this.products.add(product);
    }

    public void removeProduct(CartItem product) {
        this.products.remove(product);
    }

    public void emptyCart(){
        this.products = null;
    }
}
