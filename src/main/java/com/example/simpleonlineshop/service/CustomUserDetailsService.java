package com.example.simpleonlineshop.service;

import com.example.simpleonlineshop.data.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(user -> org.springframework.security.core.userdetails.User.builder()
//                        .username(user.getEmail())   // login with email
//                        .password(user.getPassword()) // already encoded!
////                        .roles("USER") // assign default role
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
//    }
//}
//
