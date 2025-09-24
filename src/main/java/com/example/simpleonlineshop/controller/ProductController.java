package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.entity.Product;
import com.example.simpleonlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final int SIZE = 10;
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page, @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (logout != null) {
            model.addAttribute("logout_msg", "Logged out successfully!");
        }

        List<Product> products = productService.getAllProducts(page, SIZE);
        long total = productService.getTotalProducts();
        int totalPages = (int) Math.ceil((double) total / SIZE);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "products";
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail";
    }
    @GetMapping("/search")
    public String viewProductsByName(@RequestParam("q") String query, @RequestParam(defaultValue = "0") int page,
                                     Model model) {

        List<Product> products =  productService.findByProductName(query, page, SIZE);
        long total = productService.getTotalProductsByName(query);
        int totalPages = (int) Math.ceil((double) total / SIZE);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "products";
    }


}
