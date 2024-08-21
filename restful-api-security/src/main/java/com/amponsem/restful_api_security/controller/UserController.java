package com.amponsem.restful_api_security.controller;

import com.amponsem.restful_api_security.model.Users;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @GetMapping("/")
    public String test() {
        return "How secured is your system";
    }

    @PostMapping("/create")
    public String create(@Valid @RequestBody Users user) {
        // Handle creation logic
        return "User created successfully";
    }

}


