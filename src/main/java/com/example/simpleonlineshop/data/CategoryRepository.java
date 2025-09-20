package com.example.simpleonlineshop.data;

import com.example.simpleonlineshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
