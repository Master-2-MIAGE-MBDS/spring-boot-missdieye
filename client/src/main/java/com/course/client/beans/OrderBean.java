package com.course.client.beans;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class OrderBean {
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Double totalAmount;

    private List<OrderItemBean> products;

    public OrderBean() {
    }

    public OrderBean(Date date,Double totalAmount) {
        this.date=date;
        this.totalAmount=totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderItemBean> getProducts() {
        return products;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void addProduct(OrderItemBean product) {
        this.products.add(product);
    }


}
