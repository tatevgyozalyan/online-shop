package com.example.simpleonlineshop.data;

import com.example.simpleonlineshop.entity.CartItem;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}