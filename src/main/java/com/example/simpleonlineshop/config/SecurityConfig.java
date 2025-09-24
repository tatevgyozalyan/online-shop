package com.example.simpleonlineshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/.well-known/**", "/logout" , "/loginAfterReg/**", "/search", "/products", "/categories/**", "/products/**", "/css/**", "/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/products?logout=true")
//                                .logoutSuccessUrl("/products")
//                        .deleteCookies("JSESSIONID")
                        .permitAll()
//                ).sessionManagement(session -> session
//                .maximumSessions(1)          // only 1 session per user
//                .maxSessionsPreventsLogin(true) // prevent new login if already active
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}