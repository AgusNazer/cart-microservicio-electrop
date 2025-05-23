package com.agusdev.cart.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.agusdev.cart.model.Cart;
import com.agusdev.cart.model.CartItem;
import com.agusdev.cart.service.ICartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
    @Operation(summary = "add item to cart", description = "pasarle productId para agregar el item al carrito")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        System.out.println("Item"  + cartItem + " agregado al carrito");
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
    //Cehckea que haya stock para hacer un checkout
    @PostMapping("/{cartId}/checkout")
    @Operation(summary = "Checkout del carrito", description = "Procesa el checkout del carrito con el ID proporcionado, validando stock y generando la venta.")
    public ResponseEntity<String> checkout(@PathVariable Long cartId) {
        try {
            boolean success = cartService.checkoutCart(cartId);
            if (success) {
                return ResponseEntity.ok("Checkout successful. Sale created.");
            } else {
                return ResponseEntity.badRequest().body("Checkout failed: insufficient stock.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Checkout failed: " + e.getMessage());
        }
    }

}
