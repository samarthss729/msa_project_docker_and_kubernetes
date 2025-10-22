package com.example.msa_project.controller;

import com.example.msa_project.service.JobListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final JobListingService jobListingService;

	public AdminController(JobListingService jobListingService) {
		this.jobListingService = jobListingService;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> doAdminLogin(@RequestBody Map<String, String> loginData) {
		String username = loginData.get("username");
		String password = loginData.get("password");
		
		Map<String, Object> response = new HashMap<>();
		
		if ("admin".equals(username) && "admin123".equals(password)) {
			response.put("success", true);
			response.put("message", "Admin login successful");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Invalid admin credentials");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/jobs")
	public ResponseEntity<Map<String, Object>> getJobs() {
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("success", true);
			response.put("jobs", jobListingService.listAll());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Failed to load jobs");
			return ResponseEntity.badRequest().body(response);
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


