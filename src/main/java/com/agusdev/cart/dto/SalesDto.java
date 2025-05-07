package com.agusdev.cart.dto;

import com.agusdev.cart.model.CartItem;
import java.util.List;

public class SalesDto {
    private List<CartItem> items;
    private Double total;

    // Getters y setters
    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}
