package com.course.order.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

//@FeignClient(name = "ms-product", url = "localhost:8091")
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    //@OneToMany(cascade = CascadeType.ALL)
   // private List<CartItem> products;
}
