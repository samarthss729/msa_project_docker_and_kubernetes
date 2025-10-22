package com.example.msa_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "job_listings")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
}


