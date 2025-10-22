package com.example.adminservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> doAdminLogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> response = new HashMap<>();

        // ✅ Hardcoded admin credentials
        if ("admin".equals(username) && "admin123".equals(password)) {
            response.put("success", true);
            response.put("message", "Admin login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid admin credentials");
            // Better to return 401 Unauthorized than 400 Bad Request
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> adminLogout() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
