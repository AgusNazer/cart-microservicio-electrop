package com.agusdev.cart.dto;

import java.math.BigDecimal;

public class CartItemDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal priceAtTime;
    private BigDecimal subtotal;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, Integer quantity, BigDecimal priceAtTime, BigDecimal subtotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.subtotal = subtotal;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
