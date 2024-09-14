package com.example.program_storage_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.program_storage_service.model.CommonSchemaProgram;
import com.example.program_storage_service.repository.IngestionDBRepository;

@Service
public class IngestionDBService {
    @Autowired
    private IngestionDBRepository ingestionRepository;

    public void save(CommonSchemaProgram ingestionModel) {
        ingestionRepository.save(ingestionModel);
    }

    public void update(CommonSchemaProgram ingestionModel) {
        ingestionRepository.save(ingestionModel); 
    }

    public Optional<CommonSchemaProgram> findByProgramId(String programId) {
        return ingestionRepository.findById(programId);
    }
}
