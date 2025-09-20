package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.ProductRepository;
import com.example.simpleonlineshop.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> getAllProducts(int page, int size) {
        return entityManager.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
                .setFirstResult(page * size)  // OFFSET
                .setMaxResults(size)          // LIMIT
                .getResultList();
    }

    public long getTotalProducts() {
        return entityManager.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();
    }
    public List<Product> getProductsByCategory(Long categoryId, int page, int size) {
        return entityManager.createQuery(
                        "SELECT p FROM Product p WHERE p.category.id = :param ORDER BY p.id",
                        Product.class)
                .setParameter("param", categoryId)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByProductName(String name, int page, int size) {
        return entityManager.createQuery(
                        "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:param) ORDER BY p.id",
                        Product.class)
                .setParameter("param", "%" + name + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
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
