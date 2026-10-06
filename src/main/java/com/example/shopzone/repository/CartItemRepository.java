package com.example.shopzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.CartItem;
import com.example.shopzone.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByProduct(Product product);
    void deleteByProduct(Product product);
}