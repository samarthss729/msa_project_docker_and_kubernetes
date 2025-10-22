package com.example.msa_project.service;

import com.example.msa_project.model.UserData;
import com.example.msa_project.repository.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

	private final UserDataRepository userDataRepository;

	public RegistrationService(UserDataRepository userDataRepository) {
		this.userDataRepository = userDataRepository;
	}

	public UserData register(UserData input) {
		return userDataRepository.save(input);
	}
}








