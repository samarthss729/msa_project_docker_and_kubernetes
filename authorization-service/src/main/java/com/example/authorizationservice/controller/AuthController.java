package com.example.authorizationservice.controller;

import com.example.authorizationservice.model.UserData;
import com.example.authorizationservice.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDataRepository userDataRepository;

    // ✅ Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserData userData) {
        // Check if username already exists
        if (userDataRepository.findByUsername(userData.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        // Save user with default role USER
        userData.setRole("USER");
        userDataRepository.save(userData);

        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ Login endpoint (basic check)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserData loginRequest) {
        return userDataRepository.findByUsername(loginRequest.getUsername())
                .map(user -> {
                    if (user.getPassword().equals(loginRequest.getPassword())) {
                        return ResponseEntity.ok("Login successful!");
                    } else {
                        return ResponseEntity.badRequest().body("Invalid password!");
                    }
                })
                .orElse(ResponseEntity.badRequest().body("User not found!"));
    }
}
