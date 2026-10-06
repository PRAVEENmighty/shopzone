package com.example.shopzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.shopzone.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "home";
    }
}