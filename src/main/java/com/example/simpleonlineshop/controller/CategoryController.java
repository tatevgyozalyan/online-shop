package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.data.CategoryRepository;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    public CategoryController(CategoryRepository categoryRepository, ProductService productService){
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }
    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String viewCategory(@PathVariable Long id,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "0") int size,
                               Model model) {
        if (size < 1) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Product> productPage = productService.getProductsByCategory(id, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "products";
    }
}
