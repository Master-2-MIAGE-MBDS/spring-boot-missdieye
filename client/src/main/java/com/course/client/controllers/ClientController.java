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

    @GetMapping("cart-detail")
    public String cartDetail(Model model ){
        Optional<CartBean> cart = msCartProxy.getCart(1L);
       /* List<CartItemBean> productCart=cart.get().getProducts();
        List itemCart = null;
        for (int i=0;i<productCart.size();i++){
            Optional<ProductBean> product = msProductProxy.get(productCart.get(i).productId);
            itemCart.add(product);
        }
*/
        model.addAttribute("cart", cart.get());
        //model.addAttribute("cart", itemCart);
        return "cartdetail";
    }

    @PostMapping( "/cart/{productId}")
    public String addCart(Model model,@PathVariable Long productId ){
        /*Optional<CartItemBean> cartItemFound =  msCartProxy.getItemCart(productId);
        System.out.println("éja"+ cartItemFound);

        if (cartItemFound!=null){
            System.out.println("ajjjjjjouté déja");
        }else{*/
            CartItemBean cartItem= new CartItemBean(productId,1);
            msCartProxy.addProductToCart(1L,cartItem);

            Optional<CartBean> cart = msCartProxy.getCart(1L);

            model.addAttribute("cart", cart.get());
        //}

        return "cartdetail";
    }

    @GetMapping("orders")
    public String orders(Model model ) {
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        model.addAttribute("cart", cart.get());
        return "orders";
    }

    @GetMapping("order-detail/{orderId}")
    public String orderDetail(Model model,@PathVariable Long orderId  ){
        Optional<CartBean> cart = msCartProxy.getCart(1L);
       /* List<CartItemBean> productCart=cart.get().getProducts();
        List itemCart = null;
        for (int i=0;i<productCart.size();i++){
            Optional<ProductBean> product = msProductProxy.get(productCart.get(i).productId);
            itemCart.add(product);
        }
*/
        model.addAttribute("cart", cart.get());
        //model.addAttribute("cart", itemCart);
        return "orderdetail";
    }

    }





