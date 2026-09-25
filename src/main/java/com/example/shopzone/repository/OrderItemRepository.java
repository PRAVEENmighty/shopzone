package com.example.shopzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}