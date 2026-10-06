package com.example.shopzone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopzone.entity.Product;
import com.example.shopzone.repository.CartItemRepository;
import com.example.shopzone.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository ProductRepository;
    private final CartItemRepository CartItemRepository;

    public ProductService(ProductRepository ProductRepository,
                           CartItemRepository CartItemRepository) {

        this.ProductRepository = ProductRepository;
        this.CartItemRepository = CartItemRepository;
    }

    // Add Product
    public Product saveProduct(Product product) {
        return ProductRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return ProductRepository.findAll();
    }

 // Search Products
    public List<Product> searchProducts(String name) {
        return ProductRepository.findByNameContainingIgnoreCase(name);
    }

    // Filter By Category
    public List<Product> filterByCategory(String category) {
        return ProductRepository.findByCategoryIgnoreCase(category);
    }
    
    // Get Product By ID
    public Product getProductById(Long id) {
        return ProductRepository.findById(id).orElse(null);
    }

    // Delete Product
    @Transactional
    public void deleteProduct(Long id) {

        Product product = ProductRepository.findById(id).orElse(null);

        if (product != null) {
            CartItemRepository.deleteByProduct(product);
            ProductRepository.deleteById(id);
        }
    }
}