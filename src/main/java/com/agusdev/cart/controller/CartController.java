package com.agusdev.cart.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.agusdev.cart.model.Cart;
import com.agusdev.cart.model.CartItem;
import com.agusdev.cart.service.ICartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
//Swagger
@Tag(name = "Cart", description = "API for managing the cart")

public class CartController {

    @Autowired
    private ICartService cartService;

    // Obtener todos los carritos
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    // Crear un carrito vacío
    @PostMapping
    public Cart createCart() {
        return cartService.createCart();
    }

    // Obtener un carrito por ID
    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    // Agregar un item a un carrito
    @PostMapping("/{cartId}/items")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        return cartService.addItemToCart(cartId, cartItem);
    }

    // Eliminar un item del carrito
    @DeleteMapping("/{cartId}/items/{itemId}")
    public void removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItemFromCart(cartId, itemId);
    }

    // Actualizar la cantidad de un item en el carrito
    @PutMapping("/{cartId}/items/{itemId}")
    public void updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateItemQuantity(cartId, itemId, quantity);
    }

    // Eliminar un carrito completo
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
    }
}
