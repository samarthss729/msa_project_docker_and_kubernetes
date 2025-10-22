package com.example.msa_project.service;

import com.example.msa_project.model.JobDetails;
import com.example.msa_project.model.JobListing;
import com.example.msa_project.repository.JobDetailsRepository;
import com.example.msa_project.repository.JobListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		// Let @MapsId propagate the primary key from JobListing; avoid pre-setting id
		details.setJobListing(saved);
		details.setId(null);
		jobDetailsRepository.saveAndFlush(details);
		return saved.getId();
	}

	public JobListing findListing(Long id) {
		return jobListingRepository.findById(id).orElseThrow();
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
		// Delete child first due to FK constraint, then parent
		if (jobDetailsRepository.existsById(id)) {
			jobDetailsRepository.deleteById(id);
		}
		if (jobListingRepository.existsById(id)) {
			jobListingRepository.deleteById(id);
		}
	}
}


