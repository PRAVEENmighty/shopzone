package com.example.shopzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryIgnoreCase(String category);
    

}