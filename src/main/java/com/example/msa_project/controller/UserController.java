package com.example.msa_project.controller;

import com.example.msa_project.model.User;
import com.example.msa_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAll() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		User created = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}








