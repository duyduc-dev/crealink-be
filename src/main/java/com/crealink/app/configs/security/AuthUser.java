package com.crealink.app.configs.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crealink.app.dto.user.UserDto;

import lombok.Data;

@Data
public class AuthUser implements UserDetails {
    private String id;
    private UserDto currentUser;

    public AuthUser(UserDto user) {
        this.id = user.id();
        this.currentUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return id;
    }
}
