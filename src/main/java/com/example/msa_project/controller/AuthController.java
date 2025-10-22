package com.example.msa_project.controller;

import com.example.msa_project.model.UserData;
import com.example.msa_project.repository.UserDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final UserDataRepository userDataRepository;

	public AuthController(UserDataRepository userDataRepository) {
		this.userDataRepository = userDataRepository;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> doLogin(@RequestBody Map<String, String> loginData) {
		String username = loginData.get("username");
		String password = loginData.get("password");
		
		Optional<UserData> match = userDataRepository.findByUsernameAndPassword(username, password);
		Map<String, Object> response = new HashMap<>();
		
		if (match.isPresent()) {
			response.put("success", true);
			response.put("message", "Login successful");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Invalid credentials");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			// Check if username already exists
			if (userDataRepository.findByUsername(userData.getUsername()).isPresent()) {
				response.put("success", false);
				response.put("message", "Username already exists");
				return ResponseEntity.badRequest().body(response);
			}
			
			userDataRepository.save(userData);
			response.put("success", true);
			response.put("message", "Registration successful");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Registration failed");
			return ResponseEntity.badRequest().body(response);
		}
	}
}











