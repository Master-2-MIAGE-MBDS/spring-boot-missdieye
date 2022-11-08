package com.course.cart.controllers;

import com.course.cart.domain.Cart;
import com.course.cart.domain.CartItem;
import com.course.cart.repositories.CartItemRepository;
import com.course.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping(value = "/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData)
    {
        Cart cart = cartRepository.save(new Cart());

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");

        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value = "/cart/{id}")
    public Optional<Cart> getCart(@PathVariable Long id)
    {
        Optional<Cart> cart = cartRepository.findById(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        return cart;
    }


    @PostMapping(value = "/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        Cart cart = cartRepository.getOne(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");


        cart.addProduct(cartItem);

        cartRepository.save(cart);

        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }
    
    // Remove a product from the cart
    @DeleteMapping(value = "/deleteItem-cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> removeProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        Cart cart = cartRepository.getOne(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        cart.removeProduct(cartItem);

        cartRepository.save(cart);

        return new ResponseEntity<CartItem>(cartItem, HttpStatus.OK);
    }


    // Post en lui donnant l'id de la carte et l'id du produit pour modifier 
    @PostMapping(value="/updateProductQuantity/{idCart}/{idProduct}")
    public  void updateProductQuantity(@PathVariable Long idCart,@PathVariable Long idProduct,@RequestParam Boolean minus ){
        // Search cart with id idCart
        Cart cart = cartRepository.getOne(idCart);
        System.out.println("minusjjjjjj "+ minus);

        // au cas où le panier n'est pas trouvé
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        //Chercher le produit qu'on veut modifier sa quantité et dont son id est idProduct
        for (CartItem cartItem : cart.getProducts()) {
            // Si la le current id est égal à l'id du produit qu'on cherche
            if (cartItem.getProductId()==idProduct){
                //On modifie sa quantité pour en diminuer ou ajouter 1
                if(minus==true) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }else{
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
                // Puis on enregistre l'item de la carte
                cartItemRepository.save(cartItem);
            }
        }
    }

    @PostMapping(value = "/cartItems/{idCart}")
    public void deleteItemsCart(@PathVariable Long idCart){
        Cart cart = cartRepository.getOne(idCart);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        cart.getProducts().clear();
        cartRepository.save(cart);

    }
}
