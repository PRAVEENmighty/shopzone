package com.example.shopzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopzone.entity.CartItem;
import com.example.shopzone.entity.Product;
import com.example.shopzone.repository.CartItemRepository;
import com.example.shopzone.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public ProductController(
            ProductService productService,
            CartItemRepository cartItemRepository) {

        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    // Show All Products
    @GetMapping("/products")
    public String products(Model model) {

        List<Product> products =
                productService.getAllProducts();

        model.addAttribute("products", products);

        return "products";
    }

    // Search Products
    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String name,
            Model model) {

        List<Product> products =
                productService.searchProducts(name);

        model.addAttribute("products", products);

        return "products";
    }
 // Filter Products By Category
    @GetMapping("/category")
    public String filterByCategory(
            @RequestParam String category,
            Model model) {

        List<Product> products =
                productService.filterByCategory(category);

        model.addAttribute("products", products);

        return "products";
    }

    // Add Product Page
    @GetMapping("/add-product")
    public String addProductPage(Model model) {

        model.addAttribute("product", new Product());

        return "add-product";
    }

    // Save Product
    @PostMapping("/add-product")
    public String saveProduct(Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

    // Delete Product
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/products";
    }

    // Edit Product Page
    @GetMapping("/edit-product/{id}")
    public String editProductPage(
            @PathVariable Long id,
            Model model) {

        Product product =
                productService.getProductById(id);

        model.addAttribute("product", product);

        return "edit-product";
    }

    // Update Product
    @PostMapping("/edit-product")
    public String updateProduct(Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

    // Product Details
    @GetMapping("/product/{id}")
    public String productDetails(
            @PathVariable Long id,
            Model model) {

        Product product =
                productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-details";
    }

    // Add Product To Cart
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {

        Product product =
                productService.getProductById(id);

        if (product == null) {
            return "redirect:/products";
        }

        CartItem existingItem =
                cartItemRepository.findByProduct(product);

        if (existingItem != null) {

            if (existingItem.getQuantity()
                    < product.getQuantity()) {

                existingItem.setQuantity(
                        existingItem.getQuantity() + 1
                );

                cartItemRepository.save(existingItem);
            }

        } else {

            if (product.getQuantity() > 0) {

                CartItem cartItem = new CartItem();

                cartItem.setProduct(product);
                cartItem.setQuantity(1);

                cartItemRepository.save(cartItem);
            }
        }

        return "redirect:/cart";
    }
}