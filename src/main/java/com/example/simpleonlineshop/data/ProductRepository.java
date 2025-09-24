package com.example.simpleonlineshop.data;

import com.example.simpleonlineshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// for now not using CategoryRepo

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String name);

}
