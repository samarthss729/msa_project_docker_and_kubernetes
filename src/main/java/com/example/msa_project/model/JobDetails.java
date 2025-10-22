package com.example.msa_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "job_details")
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

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public JobListing getJobListing() { return jobListing; }
	public void setJobListing(JobListing jobListing) { this.jobListing = jobListing; }

	public String getIntro() { return intro; }
	public void setIntro(String intro) { this.intro = intro; }

	public String getWebsite() { return website; }
	public void setWebsite(String website) { this.website = website; }

	public String getFullDescription() { return fullDescription; }
	public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	public String getExperience() { return experience; }
	public void setExperience(String experience) { this.experience = experience; }

	public String getSkillSet() { return skillSet; }
	public void setSkillSet(String skillSet) { this.skillSet = skillSet; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	public String getIndustryType() { return industryType; }
	public void setIndustryType(String industryType) { this.industryType = industryType; }

	public String getDepartment() { return department; }
	public void setDepartment(String department) { this.department = department; }

	public String getEmploymentType() { return employmentType; }
	public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

	public String getRoleCategory() { return roleCategory; }
	public void setRoleCategory(String roleCategory) { this.roleCategory = roleCategory; }

	public String getUgEducation() { return ugEducation; }
	public void setUgEducation(String ugEducation) { this.ugEducation = ugEducation; }

	public String getPgEducation() { return pgEducation; }
	public void setPgEducation(String pgEducation) { this.pgEducation = pgEducation; }

	public String getKeySkills() { return keySkills; }
	public void setKeySkills(String keySkills) { this.keySkills = keySkills; }
}























