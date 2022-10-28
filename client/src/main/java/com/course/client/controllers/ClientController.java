package com.course.client.controllers;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import com.course.client.beans.ProductBean;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    private MsCartProxy msCartProxy;

    @RequestMapping("/")
    public String index(Model model) {

        List<ProductBean> products =  msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/product-detail/{productId}")
    public String productDetail(Model model,@PathVariable Long productId ){
        Optional<ProductBean> product = msProductProxy.get(productId);

        model.addAttribute("product", product.get());
        return "productdetail";
    }

    @GetMapping("card-detail")
    public String cardDetail(Model model ){
        Optional<CartBean> card = msCartProxy.getCart(Long.valueOf(1));

        model.addAttribute("card", card.get());
        return "carddetail";
    }

    @GetMapping("/cart/add/{productId}")
    public String addCart(Model model,@PathVariable Long productId ){
        CartItemBean cartItem= new CartItemBean(Long.valueOf(1),productId,1);
       msCartProxy.addProductToCart(Long.valueOf(1),);
        Optional<CartBean> card = msCartProxy.getCart(Long.valueOf(1));

        model.addAttribute("card", card.get());
        return "carddetail";
    }


}





