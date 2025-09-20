package com.example.simpleonlineshop.config;

import com.example.simpleonlineshop.data.CategoryRepository;
import com.example.simpleonlineshop.entity.Category;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@ControllerAdvice
public class GlobalModelAttributes {

    private final CategoryRepository categoryRepository;

    public GlobalModelAttributes(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute
    public void addCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
    }
//    @ModelAttribute
//    public void authorized(Model model) {
//        model.addAttribute("isAuthorized", true);
//    }
}
