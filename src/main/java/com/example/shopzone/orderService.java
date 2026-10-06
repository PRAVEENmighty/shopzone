package com.example.shopzone;

import org.springframework.stereotype.Service;

import com.example.shopzone.entity.Order;
import com.example.shopzone.repository.OrderRepository;

@Service
public class orderService {

    private final OrderRepository orderRepository;

    public orderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}