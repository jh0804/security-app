package com.metacoding.securityapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// localhost:8080/user/asbadkjfale
// localhost:8080/join-form
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // H2 Console은 iframe으로 동작한다. Security는 iframe을 차단하기 때문에 H2 Console을 확인하려면 설정 필요
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        // CSRF Filter
        http.csrf(configure -> configure.disable());

        // Username Password Authentication Filter
        http.formLogin(form -> form
                        .loginPage("/login-form")
//                .usernameParameter("email")
                        .loginProcessingUrl("/login") // username=ssar&password=1234
                        .defaultSuccessUrl("/main")
        );

        // Aware Filter
        // /main 인증이 필요해, /admin ADMIN 권한이 필요해, /user USER 권한이 필요해
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/main").authenticated()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );

        return http.build();
    }
}