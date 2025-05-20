package com.agusdev.cart.service;

import com.agusdev.cart.model.Cart;
import com.agusdev.cart.model.CartItem;
import com.agusdev.cart.repository.CartRepository;
import com.agusdev.cart.repository.CartItemRepository;
import com.agusdev.cart.repository.ProductClient;
import com.agusdev.cart.repository.SalesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agusdev.cart.dto.ProductDto; //dto de cart
import com.agusdev.cart.dto.SalesDto;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private SalesClient salesClient;





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

            // Validación y log
            if (cartItem.getProductId() == null) {
                throw new RuntimeException("El producto no tiene ID, no se puede buscar.");
            }
            System.out.println("🧪 Product ID recibido: " + cartItem.getProductId());

            // Llamada al servicio products usando Feign
            ProductDto productDto = productClient.getProductById(cartItem.getProductId());
            if (productDto == null) {
                throw new RuntimeException("Producto no encontrado en el servicio products.");
            }

            cartItem.setCart(cart); // Asocia el cart al cartItem
            cart.getItems().add(cartItem); // Agrega el item a la lista
            cartItemRepository.save(cartItem); // Guarda el item
            return cartRepository.save(cart); // Guarda y devuelve el carrito actualizado
        }
        throw new RuntimeException("Carrito no encontrado con ID: " + cartId);
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
    @Override
    public boolean checkoutCart(Long cartId) {
        // 1️⃣ Buscar el carrito
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Carrito no encontrado");
        }

        // Lista para guardar los ítems válidos
        List<CartItem> validItems = new ArrayList<>();
        List<CartItem> invalidItems = new ArrayList<>();

        // 2️⃣ Verificar stock de cada ítem y filtrar productos inexistentes
        for (CartItem item : cart.getItems()) {
            try {
                ProductDto product = productClient.getProductById(item.getProductId());

                if (product == null) {
                    // Producto no encontrado, lo agregamos a inválidos
                    invalidItems.add(item);
                    continue; // Saltamos al siguiente ítem
                }

                // Verificar stock
                if (product.getStock() < item.getQuantity()) {
                    // Si no hay suficiente stock, agregamos a inválidos
                    invalidItems.add(item);
                    continue;
                }

                // Si llegamos aquí, el ítem es válido
                validItems.add(item);

            } catch (Exception e) {
                // Si falla la llamada al servicio de productos, marcamos este ítem como inválido
                invalidItems.add(item);
            }
        }

        // Si no hay ítems válidos, no seguimos con el checkout
        if (validItems.isEmpty()) {
            throw new RuntimeException("No hay productos válidos en el carrito");
        }

        // 3️⃣ Descontar stock solo de productos válidos
        for (CartItem item : validItems) {
            // Aseguramos que solo descontamos el stock de productos válidos
            productClient.decreaseStock(item.getProductId(), item.getQuantity());
        }

        // 4️⃣ Crear la venta en el servicio de ventas
        SalesDto saleDto = new SalesDto();
        saleDto.setItems(validItems); // Solo mandamos los ítems válidos

        // Calcula el total usando BigDecimal
        BigDecimal total = validItems.stream()
                .map(i -> i.getPriceAtTime().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Como tu SalesDto espera un Double, conviertes al final
        saleDto.setTotal(total.doubleValue());

        // Llamada al servicio de ventas para crear la venta
        salesClient.createSale(saleDto);

        // 5️⃣ Vaciar los ítems inválidos del carrito
        // Elimina los ítems inválidos directamente de la base de datos (si quieres mantenerlos huérfanos)
        cart.getItems().removeAll(invalidItems);

        // 6️⃣ Guardar los cambios del carrito (con solo los ítems válidos)
        cartRepository.save(cart);

        // Si llegamos aquí, todo salió bien
        return true;
    }





}

