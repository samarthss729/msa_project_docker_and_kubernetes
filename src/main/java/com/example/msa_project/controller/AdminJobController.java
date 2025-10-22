package com.example.msa_project.controller;

import com.example.msa_project.model.JobDetails;
import com.example.msa_project.model.JobListing;
import com.example.msa_project.service.JobAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminJobController {

	private final JobAdminService jobAdminService;

	public AdminJobController(JobAdminService jobAdminService) {
		this.jobAdminService = jobAdminService;
	}

	@PostMapping("/jobs")
	public ResponseEntity<Map<String, Object>> createJob(@RequestBody Map<String, Object> jobData) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			JobListing listing = new JobListing();
			listing.setJobTitle((String) jobData.get("jobTitle"));
			listing.setCompany((String) jobData.get("company"));
			listing.setRole((String) jobData.get("role"));
			listing.setDescription((String) jobData.get("description"));
			listing.setLogoPath((String) jobData.get("logoPath"));
			
			JobDetails details = new JobDetails();
			details.setIntro((String) jobData.get("intro"));
			details.setWebsite((String) jobData.get("website"));
			details.setFullDescription((String) jobData.get("fullDescription"));
			details.setLocation((String) jobData.get("location"));
			details.setExperience((String) jobData.get("experience"));
			details.setSkillSet((String) jobData.get("skillSet"));
			details.setRole((String) jobData.get("role"));
			details.setIndustryType((String) jobData.get("industryType"));
			details.setDepartment((String) jobData.get("department"));
			details.setEmploymentType((String) jobData.get("employmentType"));
			details.setRoleCategory((String) jobData.get("roleCategory"));
			details.setUgEducation((String) jobData.get("ugEducation"));
			details.setPgEducation((String) jobData.get("pgEducation"));
			details.setKeySkills((String) jobData.get("keySkills"));
			
			jobAdminService.createJobWithDetails(listing, details);
			
			response.put("success", true);
			response.put("message", "Job created successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message", e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "no message" : e.getMessage()));
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/jobs/{id}")
	public ResponseEntity<Map<String, Object>> getJob(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			JobListing listing = jobAdminService.findListing(id);
			JobDetails details = jobAdminService.findDetails(id);
			
			Map<String, Object> job = new HashMap<>();
			job.put("id", listing.getId());
			job.put("jobTitle", listing.getJobTitle());
			job.put("company", listing.getCompany());
			job.put("role", listing.getRole());
			job.put("location", details.getLocation());
			job.put("salary", "Not specified"); // JobDetails doesn't have salary field
			job.put("experience", details.getExperience());
			job.put("description", details.getFullDescription());
			job.put("requirements", details.getKeySkills());
			
			response.put("success", true);
			response.put("job", job);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Job not found");
			return ResponseEntity.badRequest().body(response);
		}
	}


	@DeleteMapping("/jobs/{id}")
	public ResponseEntity<Map<String, Object>> deleteJob(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			jobAdminService.deleteJob(id);
			response.put("success", true);
			response.put("message", "Job deleted successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Failed to delete job");
			return ResponseEntity.badRequest().body(response);
		}
	}
}


