package com.example.jobservice.service;

import com.example.jobservice.model.FinalApplication;
import com.example.jobservice.repository.FinalApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FinalApplicationService {

    private final FinalApplicationRepository repository;

    public FinalApplicationService(FinalApplicationRepository repository) {
        this.repository = repository;
    }

    public FinalApplication submit(FinalApplication application) {
        return repository.save(application);
    }

    public List<FinalApplication> listAll() {
        return repository.findAll();
    }
}


