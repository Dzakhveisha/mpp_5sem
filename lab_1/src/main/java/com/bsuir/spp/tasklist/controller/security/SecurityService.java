package com.bsuir.spp.tasklist.controller.security;

import com.bsuir.spp.tasklist.dao.model.User;
import com.bsuir.spp.tasklist.service.UserService;
import com.bsuir.spp.tasklist.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("");
        }
        return UserDetailsMapper.mapToUserDetails(user);
    }
}
