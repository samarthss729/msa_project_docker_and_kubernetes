package com.example.msa_project.repository;

import com.example.msa_project.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
	Optional<UserData> findByUsername(String username);

	Optional<UserData> findByUsernameAndPassword(String username, String password);
}


