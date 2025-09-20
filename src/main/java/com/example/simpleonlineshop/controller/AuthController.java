package com.example.simpleonlineshop.controller;

import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.UserService;
import com.example.simpleonlineshop.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping // added
public class AuthController {

    private final UserService userService;
    private final UserValidator userValidator;
    public AuthController(UserService userService, UserValidator userValidator){
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String notAuth(){
//        return "index";
        return "redirect:/products";

    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/products"; // already logged in
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (!bindingResult.getAllErrors().isEmpty()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            model.addAttribute("error", error);
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
}
