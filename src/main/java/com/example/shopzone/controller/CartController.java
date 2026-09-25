package com.example.shopzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shopzone.entity.CartItem;
import com.example.shopzone.entity.Product;
import com.example.shopzone.repository.CartItemRepository;

@Controller
public class CartController {

    private final CartItemRepository cartItemRepository;

    public CartController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // Show Cart
    @GetMapping("/cart")
    public String cartPage(Model model) {

        List<CartItem> cartItems = cartItemRepository.findAll();

        model.addAttribute("cartItems", cartItems);

        double total = cartItems.stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("total", total);

        return "cart";
    }

    // Remove Product From Cart
    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id) {

        cartItemRepository.deleteById(id);

        return "redirect:/cart";
    }

    // Increase Quantity
    @GetMapping("/increase-quantity/{id}")
    public String increaseQuantity(@PathVariable Long id) {

        CartItem cartItem =
                cartItemRepository.findById(id).orElse(null);

        if (cartItem != null) {

            Product product =
                    cartItem.getProduct();

            if (cartItem.getQuantity()
                    < product.getQuantity()) {

                cartItem.setQuantity(
                        cartItem.getQuantity() + 1
                );

                cartItemRepository.save(cartItem);
            }
        }

        return "redirect:/cart";
    }

    // Decrease Quantity
    @GetMapping("/decrease-quantity/{id}")
    public String decreaseQuantity(@PathVariable Long id) {

        CartItem cartItem =
                cartItemRepository.findById(id).orElse(null);

        if (cartItem != null) {

            if (cartItem.getQuantity() > 1) {

                cartItem.setQuantity(
                        cartItem.getQuantity() - 1
                );

                cartItemRepository.save(cartItem);
            }
        }

        return "redirect:/cart";
    }
}