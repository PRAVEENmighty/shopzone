package com.example.shopzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @GetMapping("/admin-login")
    public String adminLoginPage() {

        return "admin-login";
    }

    @PostMapping("/admin-login")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        if (username.equals("mighty") && password.equals("mighty@2026")) {

            session.setAttribute("adminLoggedIn", true);

            return "redirect:/admin-orders";
        }

        return "redirect:/admin-login?error=true";
    }

    @GetMapping("/admin-logout")
    public String adminLogout(HttpSession session) {

        session.invalidate();

        return "redirect:/admin-login";
    }
}