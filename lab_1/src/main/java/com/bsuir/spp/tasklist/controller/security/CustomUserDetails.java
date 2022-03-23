package com.bsuir.spp.tasklist.controller.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class CustomUserDetails implements UserDetails {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private final long id;
    private final String username;
    private final String password;
    private final GrantedAuthority authority;



    public CustomUserDetails(Long id, String name, String password, GrantedAuthority grantedAuthority) {
        this.id = id;
        this.username = name;
        this.password = password;
        authority = grantedAuthority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    public String getAuthority() {
        return authority.getAuthority();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

}
