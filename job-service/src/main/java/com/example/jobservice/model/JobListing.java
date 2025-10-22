package com.example.jobservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "job_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_path")
    private String logoPath;
}


