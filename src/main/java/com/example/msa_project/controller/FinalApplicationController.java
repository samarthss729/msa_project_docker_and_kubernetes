package com.example.msa_project.controller;

import com.example.msa_project.model.FinalApplication;
import com.example.msa_project.service.FinalApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:8086", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE })
public class FinalApplicationController {

	private final FinalApplicationService service;

	public FinalApplicationController(FinalApplicationService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> submitApplication(@RequestBody Map<String, String> input) {
		FinalApplication app = new FinalApplication();
		app.setFirstName(input.getOrDefault("firstName", ""));
		//java.lang.System.out.Println("Name"+input.getOrDefault("firstName", ""));
		app.setLastName(input.getOrDefault("lastName", ""));
		app.setDateOfBirth(input.getOrDefault("dateOfBirth", ""));
		app.setGender(input.getOrDefault("gender", ""));
		app.setMobileNumber(input.getOrDefault("mobileNumber", ""));
		app.setCompanyName(input.getOrDefault("companyName", ""));
		app.setQualification(input.getOrDefault("qualification", ""));
		app.setExperience(input.getOrDefault("experience", ""));

		FinalApplication saved = service.submit(app);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("id", saved.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public List<FinalApplication> list() {
		return service.listAll();
	}
}




