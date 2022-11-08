package com.course.client.proxies;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "ms-cart", url = "localhost:8092")
public interface MsCartProxy {

    @PostMapping(value = "/cart")
    public ResponseEntity<CartBean> createNewCart(@RequestBody CartBean cartData);

    @GetMapping(value = "/cart/{id}")
    public Optional<CartBean> getCart(@PathVariable Long id);

    @PostMapping(value = "/cart/{id}")
    public ResponseEntity<CartItemBean> addProductToCart(@PathVariable Long id, @RequestBody CartItemBean cartItem);

    @PostMapping(value = "/deleteItem-cart/{id}")
    public ResponseEntity<CartItemBean> removeProductToCart(@PathVariable Long id, @RequestBody CartItemBean cartItem);

    @PostMapping(value = "/cartItems/{idCart}")
    public void deleteItemsCart(@PathVariable Long idCart);

    @PostMapping(value="/updateProductQuantity/{idCart}/{idProduct}")
    public  void updateProductQuantity(@PathVariable Long idCart,@PathVariable Long idProduct, @RequestParam Boolean minus );
}
