package com.example.shopzone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shopzone.entity.Product;
import com.example.shopzone.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository ProductRepository;

    public ProductService(ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    // Add Product
    public Product saveProduct(Product product) {
        return ProductRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return ProductRepository.findAll();
    }

    // Get Product By ID
    public Product getProductById(Long id) {
        return ProductRepository.findById(id).orElse(null);
    }

    // Delete Product
    public void deleteProduct(Long id) {
        ProductRepository.deleteById(id);
    }
}