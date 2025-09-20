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
        Optional<User> existingPerson = userService.findByEmail(user.getEmail());

        if (existingPerson.isPresent() && existingPerson.get().getId() != user.getId()) {
            errors.rejectValue("email", "", "Email already taken");
        }
    }
}
