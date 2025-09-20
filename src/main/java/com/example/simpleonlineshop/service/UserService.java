package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.UserRepository;
import com.example.simpleonlineshop.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User addUser(User user){
//        user.setRegistrationDate(LocalDateTime.now());
////         create a cart for every new user
//        Cart cart = new Cart();
//        user.setCart(cart);
//        cart.setUser(user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
public void registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles("ROLE_USER");
    userRepository.save(user);
}

//    public boolean validateUser(String email, String password) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return user.isPresent() && user.get().getPassword().equals(password);
//    }
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}

}
