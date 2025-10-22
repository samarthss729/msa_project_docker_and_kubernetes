package com.example.jobservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "job_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDetails {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private JobListing jobListing;

    @Column(name = "intro")
    private String intro;

    @Column(name = "website")
    private String website;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Column(name = "location")
    private String location;

    @Column(name = "experience")
    private String experience;

    @Column(name = "skill_set")
    private String skillSet;

    @Column(name = "role")
    private String role;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "department")
    private String department;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "role_category")
    private String roleCategory;

    @Column(name = "ug_education")
    private String ugEducation;

    @Column(name = "pg_education")
    private String pgEducation;

    @Column(name = "key_skills")
    private String keySkills;
}


