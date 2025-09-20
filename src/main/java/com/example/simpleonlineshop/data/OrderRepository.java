package com.example.simpleonlineshop.data;


import com.example.simpleonlineshop.entity.Order;
import com.example.simpleonlineshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}