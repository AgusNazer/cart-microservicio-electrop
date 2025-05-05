package com.agusdev.cart.service;

import com.agusdev.cart.model.Cart;
import com.agusdev.cart.model.CartItem;

import java.util.List;

public interface ICartService {

    List<Cart> getAllCarts();

    Cart createCart(); // Agrega este método aquí

    Cart getCartById(Long id);

    Cart addItemToCart(Long cartId, CartItem cartItem);

    void removeItemFromCart(Long cartId, Long itemId);

    void updateItemQuantity(Long cartId, Long itemId, int quantity);

    void deleteCart(Long cartId);

}
