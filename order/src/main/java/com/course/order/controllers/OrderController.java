package com.course.order.controllers;

import com.course.order.domain.Orders;
import com.course.order.domain.OrderItem;
import com.course.order.repositories.OrderItemRepository;
import com.course.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    @PostMapping(value = "/order")
    public ResponseEntity<Orders> createNewOrder(@RequestBody Orders ordersData)
    {
        Orders orders = orderRepository.save(ordersData);

        if (orders == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Orders>(orders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/order/{id}")
    public Optional<Orders> getOrder(@PathVariable Long id)
    {
        Optional<Orders> order = orderRepository.findById(id);

        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        return order;
    }

    @GetMapping(value = "/orders")
    public List<Orders> getOrders()
    {
        List<Orders> order = orderRepository.findAll();

        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get orders");

        return order;
    }

    @PostMapping(value = "/order/{id}")
    @Transactional
    public ResponseEntity<OrderItem> addProductToOrder(@PathVariable Long id, @RequestBody OrderItem orderItem)
    {
        Orders orders = orderRepository.getOne(id);

        if (orders == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");


        orders.addProduct(orderItem);

        orderRepository.save(orders);

        return new ResponseEntity<OrderItem>(orderItem, HttpStatus.CREATED);
    }
}
