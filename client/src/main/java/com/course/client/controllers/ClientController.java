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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            Item item = new Item(product.get().getId(),product.get().getIllustration(),product.get().getName(),cartItem.getQuantity(),product.get().getPrice(),totalPrice);
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
        // Boucler sur la liste des produits qui existent déjà dans le panier
        for (CartItemBean cartItem : cart.get().getProducts()) {
            //Si le produit que l'on veut ajouter existe déjà
            if (cartItem.getProductId()==productId){
                exist=true;
                // On appelle la fonction updateProductQuantity qui permet de l'incrémenter de 1
                msCartProxy.updateProductQuantity(1L,productId,false);
            }
        }
        // Si le produit n'existe pas, on l'ajoute
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
            Item item = new Item(product.get().getId(),product.get().getIllustration(),product.get().getName(),orderItem.getQuantity(),product.get().getPrice(),totalPrice);
            itemsOrder.add(item);
            totalAmount+=totalPrice;
        }

        model.addAttribute("orderItems", itemsOrder);
        model.addAttribute("totalAmount", totalAmount);
        return "orderdetail";
    }

    // Valider le panier
    @PostMapping( "/validate-cart/{amount}")
    public RedirectView addOrder( @PathVariable Double amount){
        //Création de la ligne commande dans la base de données
         OrderBean orderBean=new OrderBean(new Date(),amount);
         ResponseEntity<OrderBean> order=msOrderProxy.createNewOrder(orderBean);

        Optional<CartBean> cart = msCartProxy.getCart(1L);
        for (CartItemBean cartItem : cart.get().getProducts()) {
           OrderItemBean orderItem = new OrderItemBean(cartItem.getProductId(), cartItem.getQuantity());
            msOrderProxy.addProductToOrder(order.getBody().getId(), orderItem);
         }
        msCartProxy.deleteItemsCart(1L);
        return new RedirectView("/orders");
    }

    @GetMapping ( "/minus-quantity/{productId}")
    public RedirectView minusQuantity( @PathVariable Long productId){
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        for (CartItemBean cartItem : cart.get().getProducts()) {
            if (cartItem.getProductId()==productId){
                msCartProxy.updateProductQuantity(1L,productId,true);
            }
        }
        return new RedirectView("/cart-detail");
    }

    @GetMapping ( "/plus-quantity/{productId}")
    public RedirectView plusQuantity(@PathVariable Long productId){
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        for (CartItemBean cartItem : cart.get().getProducts()) {
            if (cartItem.getProductId()==productId){
                cartItem.setQuantity(cartItem.getQuantity()+1);
                msCartProxy.updateProductQuantity(1L,productId,false);
            }
        }
        return new RedirectView("/cart-detail");
    }

    @PostMapping( "/delete-product/{productId}")
    public RedirectView deleteProduct( @PathVariable Long productId){
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        for (CartItemBean cartItem : cart.get().getProducts()) {
            if (cartItem.getProductId()==productId){
                msCartProxy.removeProductToCart(1L,productId);
            }
        }
        return new RedirectView("/cart-detail");
    }

    // Object item représentant un élément du panier et permettant d'afficher en détail le panier.
    public class Item{
        Long id;
        String name;
        int quantity;
        Double price;
        Double totalPrice;
        String illustration;

        public Item(Long id,String illustration,String name, int quantity, Double price, Double totalPrice) {
            this.id=id;
            this.illustration=illustration;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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





