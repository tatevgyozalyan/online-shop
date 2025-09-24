package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.data.CategoryRepository;
import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {
    private final int SIZE = 10;


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
    public String viewCategory(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                               Model model) {
        List<Product> products = productService.getProductsByCategory(id, page, SIZE);
        long total = productService.getTotalProductsByCategory(id);
        int totalPages = (int) Math.ceil((double) total / SIZE);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "products";
    }
}
