package com.agusdev.cart.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.agusdev.cart.dto.ProductDto;

@FeignClient(name = "products", url = "http://localhost:8762")  // Nombre del servicio registrado en Eureka
public interface ProductClient {

    @GetMapping("/products/{idProduct}")  // Ruta para obtener un producto por ID
    ProductDto getProductById(@PathVariable("idProduct") Long idProduct);

    @GetMapping("/products")  // Ruta para obtener todos los productos
    List<ProductDto> getAllProducts();

    // Método para disminuir el stock de un producto
    @PostMapping("/products/decreaseStock")
    void decreaseStock(@RequestParam Long productId, @RequestParam int quantity);
}
