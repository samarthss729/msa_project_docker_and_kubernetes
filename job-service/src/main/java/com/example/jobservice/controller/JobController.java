package com.example.jobservice.controller;

import com.example.jobservice.model.JobDetails;
import com.example.jobservice.model.JobListing;
import com.example.jobservice.service.JobAdminService;
import com.example.jobservice.service.JobListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobListingService jobListingService;
    private final JobAdminService jobAdminService;

    public JobController(JobListingService jobListingService, JobAdminService jobAdminService) {
        this.jobListingService = jobListingService;
        this.jobAdminService = jobAdminService;
    }

    @GetMapping
    public List<JobListing> listAll() {
        return jobListingService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJob(@PathVariable Long id) {
        JobListing listing = jobAdminService.findListing(id);
        JobDetails details = jobAdminService.findDetails(id);

        Map<String, Object> job = new HashMap<>();
        job.put("id", listing.getId());
        job.put("jobTitle", listing.getJobTitle());
        job.put("company", listing.getCompany());
        job.put("role", listing.getRole());
        job.put("description", listing.getDescription());
        job.put("logoPath", listing.getLogoPath());

        job.put("intro", details.getIntro());
        job.put("website", details.getWebsite());
        job.put("fullDescription", details.getFullDescription());
        job.put("location", details.getLocation());
        job.put("experience", details.getExperience());
        job.put("skillSet", details.getSkillSet());
        job.put("industryType", details.getIndustryType());
        job.put("department", details.getDepartment());
        job.put("employmentType", details.getEmploymentType());
        job.put("roleCategory", details.getRoleCategory());
        job.put("ugEducation", details.getUgEducation());
        job.put("pgEducation", details.getPgEducation());
        job.put("keySkills", details.getKeySkills());

        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createJob(@RequestBody Map<String, Object> payload) {
        JobListing listing = new JobListing();
        listing.setJobTitle((String) payload.getOrDefault("jobTitle", ""));
        listing.setCompany((String) payload.getOrDefault("company", ""));
        listing.setRole((String) payload.getOrDefault("role", ""));
        listing.setDescription((String) payload.getOrDefault("description", ""));
        listing.setLogoPath((String) payload.getOrDefault("logoPath", ""));

        JobDetails details = new JobDetails();
        details.setIntro((String) payload.getOrDefault("intro", ""));
        details.setWebsite((String) payload.getOrDefault("website", ""));
        details.setFullDescription((String) payload.getOrDefault("fullDescription", ""));
        details.setLocation((String) payload.getOrDefault("location", ""));
        details.setExperience((String) payload.getOrDefault("experience", ""));
        details.setSkillSet((String) payload.getOrDefault("skillSet", ""));
        details.setRole((String) payload.getOrDefault("role", ""));
        details.setIndustryType((String) payload.getOrDefault("industryType", ""));
        details.setDepartment((String) payload.getOrDefault("department", ""));
        details.setEmploymentType((String) payload.getOrDefault("employmentType", ""));
        details.setRoleCategory((String) payload.getOrDefault("roleCategory", ""));
        details.setUgEducation((String) payload.getOrDefault("ugEducation", ""));
        details.setPgEducation((String) payload.getOrDefault("pgEducation", ""));
        details.setKeySkills((String) payload.getOrDefault("keySkills", ""));

        Long id = jobAdminService.createJobWithDetails(listing, details);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("id", id);
        return ResponseEntity.ok(res);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteJob(@PathVariable Long id) {
        jobAdminService.deleteJob(id);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        return ResponseEntity.ok(res);
    }
}



