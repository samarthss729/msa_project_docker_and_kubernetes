package com.example.msa_project.controller;

import com.example.msa_project.model.JobDetails;
import com.example.msa_project.model.JobListing;
import com.example.msa_project.service.JobAdminService;
import com.example.msa_project.service.JobListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class PublicJobController {

    private final JobListingService jobListingService;
    private final JobAdminService jobAdminService;

    public PublicJobController(JobListingService jobListingService, JobAdminService jobAdminService) {
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
}













