package com.metacoding.securityapp.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String roles; // (USER, ADMIN), (USER), (ADMIN) // 권한 여러 가지 줄 수 있는 경우 enum으로 처리 불가능하다.

    @Builder
    public User(Integer id, String username, String password, String email, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String[] roleList = roles.split(",");

        for (String role : roleList) {
            authorities.add(() -> "ROLE_" + role); // "ROLE_" + role
        }

        return authorities;
    }
}