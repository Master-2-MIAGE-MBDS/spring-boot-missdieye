package com.course.client.controllers;

import com.course.client.beans.*;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsOrderProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrderProxy msOrderProxy;

    @RequestMapping("/")
    public String index(Model model) {

        List<ProductBean> products = msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }

    // Détails d'un produit
    @GetMapping("/product-detail/{productId}")
    public String productDetail(Model model, @PathVariable Long productId) {
        Optional<ProductBean> product = msProductProxy.get(productId);

        model.addAttribute("product", product.get());
        return "productdetail";
    }

    //Détails du panier
    @GetMapping("cart-detail")
    public String cartDetail(Model model) {
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        ArrayList itemsCart = new ArrayList();
        Double totalAmount= 0D;
        for (CartItemBean cartItem : cart.get().getProducts()) {
            Optional<ProductBean> product = msProductProxy.get(cartItem.getProductId());
            Double totalPrice=cartItem.getQuantity()*product.get().getPrice();
            Item item = new Item(product.get().getIllustration(),product.get().getName(),cartItem.getQuantity(),product.get().getPrice(),totalPrice);
            itemsCart.add(item);
            totalAmount+=totalPrice;
        }

        model.addAttribute("cartItems", itemsCart);
        model.addAttribute("totalAmount", totalAmount);
        return "cartdetail";
    }

    //Ajouter un produit au panier
    @PostMapping("/cart/{productId}")
    public RedirectView addCart(Model model, @PathVariable Long productId) {

        Optional<CartBean> cart = msCartProxy.getCart(1L);
        boolean exist = false;
        for (CartItemBean cartItem : cart.get().getProducts()) {
            if (cartItem.getProductId()==productId){
                exist=true;
                cartItem.setQuantity(cartItem.getQuantity()+1);
                msCartProxy.updateProduct(1L,productId);
            }
        }
        if (!exist){
            CartItemBean cartItem = new CartItemBean(productId, 1);
            msCartProxy.addProductToCart(1L, cartItem);
        }

        return new RedirectView("/cart-detail");
    }

    // Liste des commandes
    @GetMapping("orders")
    public String orders(Model model) {
        List<OrderBean> orders = msOrderProxy.getOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    // Détail d'une commande
    @GetMapping("order-detail/{orderId}")
    public String orderDetail(Model model, @PathVariable Long orderId) {
        Optional<OrderBean> order = msOrderProxy.getOrder(orderId);
        ArrayList itemsOrder = new ArrayList();
        Double totalAmount= 0D;
        for (OrderItemBean orderItem : order.get().getProducts()) {
            Optional<ProductBean> product = msProductProxy.get(orderItem.getProductId());
            Double totalPrice=orderItem.getQuantity()*product.get().getPrice();
            Item item = new Item(product.get().getIllustration(),product.get().getName(),orderItem.getQuantity(),product.get().getPrice(),totalPrice);
            itemsOrder.add(item);
            totalAmount+=totalPrice;
        }

        model.addAttribute("orderItems", itemsOrder);
        model.addAttribute("totalAmount", totalAmount);
        return "orderdetail";
    }

    // Valider le panier
    @PostMapping( "/validate-cart/{amount}")
    public RedirectView addOrder(Model model, @PathVariable Double amount){
        //Création de la ligne commande dans la base de données
        OrderBean orderBean=new OrderBean(new Date(),amount);
        ResponseEntity<OrderBean> order=msOrderProxy.createNewOrder(orderBean);

        Optional<CartBean> cart = msCartProxy.getCart(1L);
        for (CartItemBean cartItem : cart.get().getProducts()) {
            OrderItemBean orderItem = new OrderItemBean(cartItem.getProductId(), 1);
            msOrderProxy.addProductToOrder(order.getBody().getId(), orderItem);
        }
        msCartProxy.deleteItemCart(1L);

        return new RedirectView("/orders");

    }

    // Object item représentant un élément du panier et permettant d'afficher en détail le panier.
    public class Item{
        String name;
        int quantity;
        Double price;
        Double totalPrice;
        String illustration;

        public Item(String illustration,String name, int quantity, Double price, Double totalPrice) {
            this.illustration=illustration;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getIllustration() {
            return illustration;
        }

        public void setIllustration(String illustration) {
            this.illustration = illustration;
        }
    }


}





