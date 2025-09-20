package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.data.CategoryRepository;
import com.example.simpleonlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String viewCategory(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.getProductsByCategory(id));
        model.addAttribute("category", categoryRepository.findById(id).orElse(null));
        return "products";
    }
}
