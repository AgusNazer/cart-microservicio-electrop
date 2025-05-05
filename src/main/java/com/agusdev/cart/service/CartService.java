package com.agusdev.cart.service;

import com.agusdev.cart.model.Cart;
import com.agusdev.cart.model.CartItem;
import com.agusdev.cart.repository.CartRepository;
import com.agusdev.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart addItemToCart(Long cartId, CartItem cartItem) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cartItem.setCart(cart); // Asocia el cart al cartItem
            cart.getItems().add(cartItem); // Agrega el item a la lista
            cartItemRepository.save(cartItem); // Guarda el item
            return cartRepository.save(cart); // Guarda y devuelve el carrito actualizado
        }
        return null;
    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            cartItemRepository.delete(itemOpt.get());
        }
    }

    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
