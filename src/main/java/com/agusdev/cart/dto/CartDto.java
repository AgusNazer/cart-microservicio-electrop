package com.agusdev.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private Long id;
    private BigDecimal totalPrice;
    private List<CartItemDto> items;

    public CartDto() {
    }

    public CartDto(Long id, BigDecimal totalPrice, List<CartItemDto> items) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
