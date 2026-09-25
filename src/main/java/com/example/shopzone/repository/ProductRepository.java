package com.example.shopzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}