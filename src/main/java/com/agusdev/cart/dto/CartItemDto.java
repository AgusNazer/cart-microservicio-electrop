package com.agusdev.cart.dto;

import java.math.BigDecimal;

// solo exppones los datos necessarios al cliente por medio de este itemDTO
public class CartItemDto {
    public class CartItemDTO {
        private Long productId;
        private Integer quantity;
        private BigDecimal priceAtTime;
        private BigDecimal subtotal;

        public CartItemDTO() {
        }

        public CartItemDTO(Long productId, Integer quantity, BigDecimal priceAtTime, BigDecimal subtotal) {
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
}
