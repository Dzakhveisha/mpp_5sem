package com.bsuir.spp.tasklist.controller.web;

import com.bsuir.spp.tasklist.dao.model.User;
import com.bsuir.spp.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
public class AuthorisationController {

    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/registration")
    public User registerUser(@RequestBody User user) {
        User registerUser = userService.registrate(user);
        return registerUser;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/users/{name}")
    public User getByName(@PathVariable String name){
        return userService.getByUsername(name);
    }
}