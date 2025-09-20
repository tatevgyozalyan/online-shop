package com.example.simpleonlineshop.data;

import com.example.simpleonlineshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
