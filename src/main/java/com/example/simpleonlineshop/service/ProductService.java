package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.ProductRepository;
import com.example.simpleonlineshop.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findProductByCategoryId(categoryId, pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<Product> findByProductName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);

    }

    public long getTotalProductsByName(String name) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE LOWER(p.name) LIKE LOWER(:param)", Long.class)
                .setParameter("param", "%" + name + "%")
                .getSingleResult();
    }

    public long getTotalProductsByCategory(Long id) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.category.id = :param", Long.class)
                .setParameter("param", id)
                .getSingleResult();

    }
}
