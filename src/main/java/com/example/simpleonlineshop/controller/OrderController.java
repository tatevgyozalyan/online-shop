package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public String createOrder(@AuthenticationPrincipal User user) {
        orderService.createOrderFromCart(user);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String listOrders(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("orders", orderService.getUserOrders(user));
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model, Principal principal) {
        String username = principal.getName();
        Optional<Order>  optionalOrder = orderService.getOrderByIdAndUsername(id, username);
        if (optionalOrder.isEmpty()){
            return "redirect:/orders?error";
        }
        model.addAttribute("order", optionalOrder.get());
        return "order-detail";
    }
}
