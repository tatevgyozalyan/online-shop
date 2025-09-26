package com.example.simpleonlineshop.controller.service;

import com.example.simpleonlineshop.data.ProductRepository;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Apple iPhone");

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Samsung Galaxy");
    }

    @Test
    void testGetAllProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(product1, product2));
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.getAllProducts(pageable);

        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(product1));
        assertTrue(result.getContent().contains(product2));
        verify(productRepository).findAll(pageable);
    }

    @Test
    void testGetProductsByCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(product1));
        when(productRepository.findProductByCategoryId(1L, pageable)).thenReturn(page);

        Page<Product> result = productService.getProductsByCategory(1L, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(product1, result.getContent().get(0));
        verify(productRepository).findProductByCategoryId(1L, pageable);
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(product1, result);
        verify(productRepository).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(3L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(3L);

        assertNull(result);
        verify(productRepository).findById(3L);
    }

    @Test
    void testFindByProductName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(product1));
        when(productRepository.findByNameContainingIgnoreCase("iphone", pageable)).thenReturn(page);

        Page<Product> result = productService.findByProductName("iphone", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(product1, result.getContent().get(0));
        verify(productRepository).findByNameContainingIgnoreCase("iphone", pageable);
    }
}
