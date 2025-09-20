package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment/{orderId}")
    public String paymentForm(@PathVariable Long orderId) {
        return "payment"; // Simple form to "pay"
    }

    @PostMapping("/payment/process/{orderId}")
    public String processPayment(@PathVariable Long orderId) {
        paymentService.processPayment(orderId);
        return "redirect:/orders";
    }
}
