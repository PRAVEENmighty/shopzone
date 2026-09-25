package com.example.shopzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}