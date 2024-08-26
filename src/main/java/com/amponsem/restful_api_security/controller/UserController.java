package com.amponsem.restful_api_security.controller;

import com.amponsem.restful_api_security.model.Users;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<Users> users = new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody Users user) {
        // by default the @Validated handles the input validation
        users.add(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<String> getUserByName(@PathVariable("name") String name) {
        // Output encoding
        String safeName = Encode.forHtml(name);

        for (Users user : users) {
            if (user.getName().equalsIgnoreCase(safeName)) {
                return new ResponseEntity<>("User found: " + Encode.forHtml(user.getEmail()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Unknown user", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateUserByName(@PathVariable("name") String name, @RequestBody Users updatedUser) {
        // Output encoding
        String safeName = Encode.forHtml(name);

        for (Users user : users) {
            if (user.getName().equalsIgnoreCase(safeName)) {
                // Update user details
                user.setName(Encode.forHtml(updatedUser.getName()));
                user.setEmail(Encode.forHtml(updatedUser.getEmail()));

                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("we do not have records of this user", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteUserByName(@PathVariable("name") String name) {
        // Output encoding
        String safeName = Encode.forHtml(name);

        Iterator<Users> iterator = users.iterator();
        while (iterator.hasNext()) {
            Users user = iterator.next();
            if (user.getName().equalsIgnoreCase(safeName)) {
                iterator.remove();
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("we do not have this user in our database", HttpStatus.NOT_FOUND);
    }
}