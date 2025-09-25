package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.PaymentRepository;
import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    public boolean processPayment(Long orderId, String username) {
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        if (optionalOrder.isEmpty()){
            return false;
        }
        Order order = optionalOrder.get();
        if (!order.getUser().getUsername().equals(username)){
            return false;
        }

        if (!"PENDING".equals(order.getStatus())) {
            return false;
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotal());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        order.setStatus("PAID");
        orderService.saveOrder(order);

        return true;
    }

}
