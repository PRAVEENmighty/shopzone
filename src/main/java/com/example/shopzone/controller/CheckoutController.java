package com.example.shopzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.shopzone.entity.CartItem;
import com.example.shopzone.entity.Order;
import com.example.shopzone.entity.OrderItem;
import com.example.shopzone.entity.Product;
import com.example.shopzone.repository.CartItemRepository;
import com.example.shopzone.repository.OrderItemRepository;
import com.example.shopzone.repository.OrderRepository;
import com.example.shopzone.service.ProductService;

@Controller
public class CheckoutController {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public CheckoutController(
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductService productService) {

        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
    }

    // Checkout Page
    @GetMapping("/checkout")
    public String checkoutPage(Model model) {

        List<CartItem> cartItems =
                cartItemRepository.findAll();

        double total = cartItems.stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice()
                                * item.getQuantity())
                .sum();

        model.addAttribute("total", total);

        return "checkout";
    }

    // Place Order
    @PostMapping("/place-order")
    public String placeOrder(
            Order order,
            Model model) {

        List<CartItem> cartItems =
                cartItemRepository.findAll();

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        // Check stock before placing order
        for (CartItem cartItem : cartItems) {

            Product product =
                    cartItem.getProduct();

            int orderedQuantity =
                    cartItem.getQuantity();

            if (orderedQuantity > product.getQuantity()) {

                model.addAttribute(
                        "error",
                        product.getName()
                                + " is out of stock or insufficient stock."
                );

                return "checkout";
            }
        }

        // Calculate total
        double total = cartItems.stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice()
                                * item.getQuantity())
                .sum();

        // Set order total
        order.setTotal(total);

        // Save order
        Order savedOrder =
                orderRepository.save(order);

        // Save Order Items and Reduce Stock
        for (CartItem cartItem : cartItems) {

            Product product =
                    cartItem.getProduct();

            int orderedQuantity =
                    cartItem.getQuantity();

            // Create Order Item
            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderedQuantity);
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // Reduce Product Stock
            int remainingStock =
                    product.getQuantity()
                            - orderedQuantity;

            product.setQuantity(remainingStock);

            productService.saveProduct(product);
        }

        // Clear Cart
        cartItemRepository.deleteAll();

        return "redirect:/order-success";
    }

    // Order Success Page
    @GetMapping("/order-success")
    public String orderSuccess() {

        return "order-success";
    }
}