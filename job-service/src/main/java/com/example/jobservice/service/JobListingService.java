package com.example.jobservice.service;

import com.example.jobservice.model.JobListing;
import com.example.jobservice.repository.JobListingRepository;
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



