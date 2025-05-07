package com.agusdev.cart.repository;

import com.agusdev.cart.dto.SalesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sales") // O el nombre registrado en tu Eureka
public interface SalesClient {
    @PostMapping("/sales")
    SalesDto createSale(@RequestBody SalesDto sale);
}
