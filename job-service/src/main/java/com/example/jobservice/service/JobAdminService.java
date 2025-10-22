package com.example.jobservice.service;

import com.example.jobservice.model.JobDetails;
import com.example.jobservice.model.JobListing;
import com.example.jobservice.repository.JobDetailsRepository;
import com.example.jobservice.repository.JobListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobAdminService {

    private final JobListingRepository jobListingRepository;
    private final JobDetailsRepository jobDetailsRepository;

    public JobAdminService(JobListingRepository jobListingRepository, JobDetailsRepository jobDetailsRepository) {
        this.jobListingRepository = jobListingRepository;
        this.jobDetailsRepository = jobDetailsRepository;
    }

    @Transactional
    public Long createJobWithDetails(JobListing listing, JobDetails details) {
        JobListing saved = jobListingRepository.save(listing);
        details.setJobListing(saved);
        details.setId(null);
        jobDetailsRepository.saveAndFlush(details);
        return saved.getId();
    }

    public JobListing findListing(Long id) {
        return jobListingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    public JobDetails findDetails(Long id) {
        return jobDetailsRepository.findById(id).orElseGet(() -> {
            JobDetails d = new JobDetails();
            JobListing listing = findListing(id);
            d.setJobListing(listing);
            d.setId(id);
            return d;
        });
    }

    @Transactional
    public void updateJobWithDetails(Long id, JobListing updatedListing, JobDetails updatedDetails) {
        JobListing existing = findListing(id);
        existing.setJobTitle(updatedListing.getJobTitle());
        existing.setCompany(updatedListing.getCompany());
        existing.setRole(updatedListing.getRole());
        existing.setDescription(updatedListing.getDescription());
        existing.setLogoPath(updatedListing.getLogoPath());
        jobListingRepository.save(existing);

        JobDetails details = findDetails(id);
        details.setIntro(updatedDetails.getIntro());
        details.setWebsite(updatedDetails.getWebsite());
        details.setFullDescription(updatedDetails.getFullDescription());
        details.setLocation(updatedDetails.getLocation());
        details.setExperience(updatedDetails.getExperience());
        details.setSkillSet(updatedDetails.getSkillSet());
        details.setRole(updatedDetails.getRole());
        details.setIndustryType(updatedDetails.getIndustryType());
        details.setDepartment(updatedDetails.getDepartment());
        details.setEmploymentType(updatedDetails.getEmploymentType());
        details.setRoleCategory(updatedDetails.getRoleCategory());
        details.setUgEducation(updatedDetails.getUgEducation());
        details.setPgEducation(updatedDetails.getPgEducation());
        details.setKeySkills(updatedDetails.getKeySkills());
        details.setJobListing(existing);
        jobDetailsRepository.save(details);
    }

    @Transactional
    public void deleteJob(Long id) {
        if (jobDetailsRepository.existsById(id)) {
            jobDetailsRepository.deleteById(id);
        }
        if (jobListingRepository.existsById(id)) {
            jobListingRepository.deleteById(id);
        }
    }
}



