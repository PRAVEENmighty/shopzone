package com.example.shopzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shopzone.entity.Order;
import com.example.shopzone.entity.OrderItem;
import com.example.shopzone.repository.OrderItemRepository;
import com.example.shopzone.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderController(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Show All Orders
    @GetMapping("/orders")
    public String ordersPage(Model model) {

        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);

        return "orders";
    }

    // Admin Orders
    @GetMapping("/admin-orders")
    public String adminOrdersPage(
            Model model,
            HttpSession session) {

        Boolean adminLoggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin-login";
        }

        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);

        return "admin-orders";
    }

    // Show Order Details
    @GetMapping("/order/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Model model) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return "redirect:/orders";
        }

        List<OrderItem> orderItems =
                orderItemRepository.findAll()
                        .stream()
                        .filter(item ->
                                item.getOrder()
                                        .getId()
                                        .equals(id))
                        .toList();

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);

        return "order-details";
    }

    // Track Order
    @GetMapping("/track-order/{id}")
    public String trackOrder(
            @PathVariable Long id,
            Model model) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);

        return "track-order";
    }

    // Update Order Status
    @GetMapping("/update-order-status/{id}/{status}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String status) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }

        return "redirect:/orders";
    }
}