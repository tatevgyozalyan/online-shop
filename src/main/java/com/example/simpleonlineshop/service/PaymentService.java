package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.PaymentRepository;
import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    public Payment processPayment(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null || !"PENDING".equals(order.getStatus())) {
            return null;
        }

        // Simulate payment processing
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotal());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        order.setStatus("PAID");
        orderService.saveOrder(order);

        return payment;
    }
}
