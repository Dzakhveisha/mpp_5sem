package com.bsuir.spp.tasklist.controller.security;

import com.bsuir.spp.tasklist.dao.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsMapper {

    public static CustomUserDetails mapToUserDetails(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                new SimpleGrantedAuthority("USER")
        );
    }
}
