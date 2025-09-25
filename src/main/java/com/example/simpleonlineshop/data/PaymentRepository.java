package com.example.simpleonlineshop.data;

import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
