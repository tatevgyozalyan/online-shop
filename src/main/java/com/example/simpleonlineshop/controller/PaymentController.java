package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.service.OrderService;
import com.example.simpleonlineshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/payment/{orderId}")
    public String paymentForm(@PathVariable Long orderId, Principal principal, Model model) {
        String username = principal.getName();

        Optional<Order> orderOpt = orderService.getOrderById(orderId);

        if (orderOpt.isEmpty() || !orderOpt.get().getUser().getUsername().equals(username)) {
            return "redirect:/orders?error";
        }

        model.addAttribute("order", orderOpt.get());
        return "payment";
    }

    @PostMapping("/payment/process/{orderId}")
    public String processPayment(@PathVariable Long orderId, Principal principal) {
        String username = principal.getName();
        boolean success = paymentService.processPayment(orderId, username);
        if (success){
            return "redirect:/orders?error";
        }
        return "redirect:/orders";
    }
}
