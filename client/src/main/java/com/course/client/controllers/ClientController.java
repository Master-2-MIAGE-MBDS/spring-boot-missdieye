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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    @Autowired
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
        Optional<CartBean> card = msCartProxy.getCart(1L);
       /* List<CartItemBean> productCard=card.get().getProducts();
        List itemCard = null;
        for (int i=0;i<productCard.size();i++){
            Optional<ProductBean> product = msProductProxy.get(productCard.get(i).productId);
            itemCard.add(product);
        }
*/
        model.addAttribute("card", card.get());
        //model.addAttribute("card", itemCard);
        return "carddetail";
    }

    @PostMapping( "/cart/{productId}")
    public String addCart(Model model,@PathVariable Long productId ){
        CartItemBean cartItem= new CartItemBean(productId,1);
        msCartProxy.addProductToCart(1L,cartItem);

        Optional<CartBean> card = msCartProxy.getCart(1L);

        model.addAttribute("card", card.get());
        return "carddetail";
    }


}





