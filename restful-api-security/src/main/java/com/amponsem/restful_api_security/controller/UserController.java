package com.amponsem.restful_api_security.controller;

import com.amponsem.restful_api_security.model.Users;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private List<Users> users = new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody Users user) {
        // Input validation is automatically handled by @Validated
        users.add(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable("email") String email) {
        // Output encoding
        String safeEmail = Encode.forHtml(email);

        for (Users user : users) {
            if (user.getEmail().equalsIgnoreCase(safeEmail)) {
                return new ResponseEntity<>("User found: " + Encode.forHtml(user.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Unknown user", HttpStatus.NOT_FOUND);
    }

}