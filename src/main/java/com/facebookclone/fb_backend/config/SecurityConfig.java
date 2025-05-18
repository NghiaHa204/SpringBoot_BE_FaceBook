//package com.facebookclone.fb_backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .csrf(csrf -> csrf.disable());
//
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest().permitAll(); // 👈 Cho phép tất cả
//
//        return http.build();
//    }
//
//}
