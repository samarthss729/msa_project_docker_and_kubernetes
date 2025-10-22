package com.example.jobservice.repository;

import com.example.jobservice.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Long> {
}



