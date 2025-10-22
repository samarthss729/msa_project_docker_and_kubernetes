package com.example.msa_project.service;

import com.example.msa_project.model.JobListing;
import com.example.msa_project.repository.JobListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobListingService {

	private final JobListingRepository jobListingRepository;

	public JobListingService(JobListingRepository jobListingRepository) {
		this.jobListingRepository = jobListingRepository;
	}

	public List<JobListing> listAll() {
		return jobListingRepository.findAll();
	}
}























