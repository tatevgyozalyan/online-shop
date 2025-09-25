package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.CartService;
import com.example.simpleonlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam int quantity, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        cartService.addToCart(user, productService.getProductById(productId), quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("cartItems", cartService.getCartItems(user));
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, Principal principal) {
        String username = principal.getName(); // get currently logged-in username
        boolean removed = cartService.removeFromCart(id, username);
        if (!removed) {
            // error message if user tries to delete another user's item
            // will do later
        }
        return "redirect:/cart";
    }

}
