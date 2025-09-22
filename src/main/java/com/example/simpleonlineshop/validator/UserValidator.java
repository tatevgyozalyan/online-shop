package com.example.simpleonlineshop.validator;

import com.example.simpleonlineshop.entity.User;
import com.example.simpleonlineshop.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    UserService userService;
    public UserValidator(UserService userService){
        this.userService = userService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> existingEmail = userService.findByEmail(user.getEmail());
        Optional<User> existingUsername = userService.findByUsername(user.getUsername());

        if (existingEmail.isPresent() || existingUsername.isPresent()
//                && existingEmail.get().getId() != user.getId()
        ) {
            errors.rejectValue("email", "", "Email or Username already taken");
        }
    }
}
