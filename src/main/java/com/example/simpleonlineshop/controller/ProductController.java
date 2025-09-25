package com.example.simpleonlineshop.controller;

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
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (logout != null) {
            model.addAttribute("logout_msg", "Logged out successfully!");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "products";
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail";
    }
    @GetMapping("/search")
    public String viewProductsByName(@RequestParam("q") String query,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        if (size < 1) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Product> productPage =  productService.findByProductName(query, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "products";
    }


}
